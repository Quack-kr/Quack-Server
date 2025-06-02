package org.quack.QUACKServer.domain.review.repository;


import static org.quack.QUACKServer.domain.menu.domain.QMenu.menu;
import static org.quack.QUACKServer.domain.menu.domain.QMenuEval.menuEval;
import static org.quack.QUACKServer.domain.photos.domain.QPhotos.photos;
import static org.quack.QUACKServer.domain.restaurant.domain.QRestaurant.*;
import static org.quack.QUACKServer.domain.review.domain.QReview.*;
import static org.quack.QUACKServer.domain.review.domain.QReviewLike.*;
import static org.quack.QUACKServer.domain.user.domain.QCustomerUser.customerUser;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.menu.dto.response.MenuEvalResponse;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum.PhotoType;
import org.quack.QUACKServer.domain.review.domain.Review;
import org.quack.QUACKServer.domain.review.dto.response.ReviewInfoResponse;
import org.quack.QUACKServer.domain.review.dto.response.ReviewSimpleInfo;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum.ReviewLikeType;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<ReviewInfoResponse> findAllMyReview(Long userId, Pageable pageable) {

        JPQLQuery<Long> likeCount = JPAExpressions.select(reviewLike.count().longValue())
                .from(reviewLike)
                .where(reviewLike.likeType.eq(ReviewLikeType.LIKE),
                        reviewLike.reviewId.eq(review.reviewId));

        JPQLQuery<Long> dislikeCount = JPAExpressions.select(reviewLike.count().longValue())
                .from(reviewLike)
                .where(reviewLike.likeType.eq(ReviewLikeType.DISLIKE),
                        reviewLike.reviewId.eq(review.reviewId));


        List<Tuple> results = queryFactory
                .select(review,
                        restaurant.restaurantName,
                        Expressions.as(likeCount, "likeCount"),
                        Expressions.as(dislikeCount, "dislikeCount"))
                .from(review)
                .leftJoin(restaurant).on(restaurant.restaurantId.eq(review.restaurantId))
                .where(review.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<ReviewInfoResponse> content = new ArrayList<>();

        for (Tuple result : results) {
            Review findReview = result.get(review);

            ReviewInfoResponse reviewInfoResponse = ReviewInfoResponse.of(
                            findReview.getReviewId(),
                            findReview.getReviewContent(),
                            findReview.getCreatedAt(),
                            result.get(Expressions.numberPath(Long.class, "likeCount")),
                            result.get(Expressions.numberPath(Long.class, "dislikeCount")),
                            result.get(restaurant.restaurantName)
                    );

            content.add(reviewInfoResponse);
        }

        return content;
    }

    public List<ReviewSimpleInfo> findByRestaurantRecentReview(Long restaurantId) {

        StringPath userProfileAlias = Expressions.stringPath("userProfileImage");
        StringPath reviewImageAlias = Expressions.stringPath("reviewRepresentativeImage");

        List<Tuple> reviewInfoFetch = queryFactory
                .select(review.reviewId,
                        review.reviewContent,
                        review.createdAt,
                        customerUser.nickname,
                        ExpressionUtils.as(
                                JPAExpressions.select(photos.imageUrl)
                                        .from(photos)
                                        .where(photos.targetId.eq(customerUser.customerUserId)
                                                .and(photos.photoType.eq(PhotoType.DEFAULT_PROFILE.name()))),
                                userProfileAlias),
                        ExpressionUtils.as(JPAExpressions.select(photos.imageUrl)
                                .from(photos)
                                .where(photos.targetId.eq(review.reviewId)
                                        .and(photos.photoType.eq(PhotoType.REVIEW.name())))
                                .limit(1), reviewImageAlias)
                )
                .from(review)
                .join(customerUser).on(customerUser.customerUserId.eq(review.userId))
                .where(review.restaurantId.eq(restaurantId))
                .orderBy(review.createdAt.desc())
                .limit(3)
                .fetch();

        List<Long> reviewIds = reviewInfoFetch.stream()
                .map(t -> t.get(review.reviewId))
                .toList();

        if (reviewIds.isEmpty()) return List.of();

        List<Tuple> menuEvalFetch = queryFactory
                .select(
                        menu.menuName,
                        menuEval.menuEvalType,
                        menuEval.reviewId)
                .from(menuEval)
                .innerJoin(menu).on(menu.menuId.eq(menuEval.menuId))
                .where(menuEval.reviewId.in(reviewIds))
                .fetch();


        Map<Long, List<MenuEvalResponse>> evalMap = menuEvalFetch.stream()
                .collect(Collectors.groupingBy(
                        t -> t.get(menuEval.reviewId),
                        Collectors.mapping(t -> MenuEvalResponse.of(
                                t.get(menu.menuName),
                                t.get(menuEval.menuEvalType)
                        ), Collectors.toList())
                ));

        return reviewInfoFetch.stream()
                .map(t -> {
                    Long reviewId = t.get(review.reviewId);
                    return ReviewSimpleInfo.of(
                            t.get(userProfileAlias),
                            t.get(customerUser.nickname),
                            t.get(review.createdAt),
                            t.get(reviewImageAlias),
                            t.get(review.reviewContent),
                            evalMap.getOrDefault(reviewId, List.of())
                    );
                })
                .toList();
    }
}
