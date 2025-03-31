package org.quack.QUACKServer.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.*;
import org.quack.QUACKServer.domain.common.KeywordType;
import org.quack.QUACKServer.domain.common.LikeType;
import org.quack.QUACKServer.dto.restaurant.RecentReviewDto;
import org.quack.QUACKServer.dto.restaurant.ReviewMenuDto;
import org.quack.QUACKServer.dto.reviews.request.*;
import org.quack.QUACKServer.dto.reviews.response.ReviewLikeUpdateResponse;
import org.quack.QUACKServer.dto.reviews.response.ReviewsSelectResponse;
import org.quack.QUACKServer.repository.*;
import org.quack.QUACKServer.repository.review.ReviewMenuRepository;
import org.quack.QUACKServer.repository.review.ReviewPhotoRepository;
import org.quack.QUACKServer.repository.review.ReviewRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;
    private final UserRepository userRepository;
    private final KeywordRepository keywordRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;
    private final MenuRepository menuRepository;
    private final SavedRestaurantRepository savedRestaurantRepository;
    private final ReviewMenuRepository reviewMenuRepository;
    private final UserService userService;

    public int getReviewCountByUserId(Long userId) {
        return reviewRepository.countByUser_UserId(userId);
    }

    public double getEmpathyDecibelByUserId(Long userId) {
        Double empathyDecibel = reviewLikeRepository.aggregateEmpathyDecibelByUserId(userId);
        if (empathyDecibel == null || empathyDecibel < 0.0) {
            empathyDecibel = 0.0;
        }
        return empathyDecibel;
    }

    public List<Review> getReviewsByUserId(Long userId) {
        // iOS에서 요청이 어떻게 넘어오는지 확인하고 paging 해야함
        return reviewRepository.findAllByUser_UserId(userId);
    }

    public List<RecentReviewDto> getRecentReviewByRestaurantId(Long restaurantId) {
        List<Review> reviews = reviewRepository.findRecentReviewsByRestaurantId(restaurantId, 50,
                PageRequest.of(0, 3));

        return reviews.stream()
                .map(r -> new RecentReviewDto(
                        r.getReviewId(),
                        r.getUser().getNickname(),
                        r.getUser().getProfileImage(),
                        r.getCreatedDate(),
                        getReviewPhoto(r),
                        r.getReviewKeywords().stream().map(reviewKeyword -> reviewKeyword.getKeyword().getKeywordName()).toList(),
                        getReviewMenuDto(r)
                ))
                .toList();
    }

    /**
     * 꽉찬 / 심플 리뷰 작성 BL
     */
    @Transactional
    public void createReviews(Long restaurantId, Long userId, ReviewCreateRequest request) {

        // 1. 해당 가게에 대한 모든 리뷰 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("해당 식당이 존재하지 않습니다. restaurantId: " + restaurantId));

        // 2. 리뷰 키워드 유효성 검증
        // TODO : 초기에 keyword가 없는 경우에 keywordManager로 공통 컴포넌트로 추출
        List<Long> keywordIds = request.keywords().stream()
                .map(ReviewKeywordCreateItem::keywordId)
                .toList();

        Set<Long> existingKeywordIds = keywordRepository.findAllById(keywordIds).stream()
                .map(Keyword::getKeywordId)
                .collect(Collectors.toSet());

        List<Keyword> newKeywords = request.keywords().stream()
                .filter(keyword -> !existingKeywordIds.contains(keyword.keywordId()))
                .map(keyword -> Keyword.create(keyword.keywordName(), KeywordType.from(keyword.keywordType())))
                .toList();

        Map<Long, Keyword> keywordMap =  keywordRepository.saveAll(newKeywords).stream()
                .collect(Collectors.toMap(Keyword::getKeywordId, Function.identity()));

        // 2. 리뷰 생성
        Review reviewInsertVo = Review.create(user, restaurant, request.content());

        Review review = reviewRepository.save(reviewInsertVo);


        // TODO : NPE 발생 여지 있음 Keyword 값 초기에 넣어주는 것에 대한 SQL 실행 쿼리 구현 해야함.
        List<ReviewKeyword> reviewKeywords = request.keywords().stream()
                .map(id -> ReviewKeyword.create(review, keywordMap.get(id)))
                .toList();

        reviewKeywordRepository.saveAll(reviewKeywords);

        // 3. 먹은 메뉴 생성
        List<Menu> menus = menuRepository.findAllById(
                request.menus().stream().map(ReviewMenuCreateItem::menuId).toList());

        List<ReviewMenu> reviewMenuVos = menus.stream()
                .map(menu -> ReviewMenu.create(review, menu))
                .toList();

        reviewMenuRepository.saveAll(reviewMenuVos);


        // TODO : 사진들을 담고 있는 이미지
//        List<ReviewPhoto> reviewPhotosInsertVos = reviewPhotos.stream().toList();

//        reviewPhotoRepository.saveAll();

    }

    public ReviewsSelectResponse selectReviews(Long restaurantId, Long userId, ReviewsSelectRequest request) {

        // TODO : ENUM
        String orderBy = request.reviewOrderType();

        switch (orderBy) {
            // 1. 정렬 최신순
            case "NEWEST" -> {
                // TODO : Page -> Slice 객체
                Pageable pageable = PageRequest.of(request.pageNum(), request.pageSize(), Sort.by("createdDate").descending());
                List<Review> reviews = reviewRepository.findAllByRestaurantId(restaurantId, pageable);

                return ReviewsSelectResponse.of(userId, reviews);
            }
            // 2. 공감순
            case "MOST" -> {
                Pageable pageable = PageRequest.of(request.pageNum(), request.pageSize());

                List<Review> reviews = reviewRepository.findAllOrderByHackGongGamCountDesc(restaurantId, pageable);

                return ReviewsSelectResponse.of(userId, reviews);
            }
            default -> {
                throw new IllegalArgumentException("리뷰 조회 타입을 찾을 수 없습니다");
            }
        }

    }

    @Transactional
    public ReviewLikeUpdateResponse updateReviewLikes(Long userId, ReviewLikeUpdateRequest request) {

        Review review = reviewRepository.findById(request.reviewId())
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다"));

        reviewLikeRepository.findByReviewIdAndUserId(request.reviewId(), userId)
                .ifPresent(userReviewLike -> reviewLikeRepository.deleteById(userReviewLike.getLikeId()));

        User user = userService.getUserOrException(userId);

        switch (request.myGongGam()) {
            case "HACK_GONG_GAM" -> {
                reviewLikeRepository.save(ReviewLike.create(user, review,LikeType.HACK_GONG_GAM));
            }
            case "NO_GONG_GAM" -> {
                reviewLikeRepository.save(ReviewLike.create(user, review,LikeType.NO_GANG_GAM));}
        }

        return ReviewLikeUpdateResponse.from(reviewLikeRepository.findByReviewId(request.reviewId()));
    }
    @Transactional
    public void deleteReviewLike( Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다"));

        reviewRepository.delete(review);

    }

    private List<String> getReviewPhoto(Review review) {
        return review.getReviewPhotos()
                .stream()
                .map(ReviewPhoto::getPhotoUrl)
                .collect(Collectors.toList());
    }

    private List<ReviewMenuDto> getReviewMenuDto(Review review){
        return review.getReviewMenus().stream()
                .map(rm -> new ReviewMenuDto(rm.getMenu().getMenuName(), rm.getEvaluation()))
                .toList();

    }

}
