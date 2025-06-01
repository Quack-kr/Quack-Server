package org.quack.QUACKServer.review.repository;


import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.review.domain.Review;
import org.quack.QUACKServer.review.dto.response.ReviewInfoResponse;
import org.quack.QUACKServer.review.enums.ReviewEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.quack.QUACKServer.restaurant.domain.QRestaurant.restaurant;
import static org.quack.QUACKServer.review.domain.QReview.review;
import static org.quack.QUACKServer.review.domain.QReviewLike.reviewLike;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<ReviewInfoResponse> findAllMyReview(Long userId, Pageable pageable) {

        JPQLQuery<Long> likeCount = JPAExpressions.select(reviewLike.count().longValue())
                .from(reviewLike)
                .where(reviewLike.likeType.eq(ReviewEnum.ReviewLikeType.LIKE),
                        reviewLike.reviewId.eq(review.reviewId));

        JPQLQuery<Long> dislikeCount = JPAExpressions.select(reviewLike.count().longValue())
                .from(reviewLike)
                .where(reviewLike.likeType.eq(ReviewEnum.ReviewLikeType.DISLIKE),
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
}
