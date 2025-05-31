package org.quack.QUACKServer.domain.review.repository;


import static org.quack.QUACKServer.domain.restaurant.domain.QRestaurant.*;
import static org.quack.QUACKServer.domain.review.domain.QReview.*;
import static org.quack.QUACKServer.domain.review.domain.QReviewLike.*;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.review.domain.Review;
import org.quack.QUACKServer.domain.review.dto.response.ReviewInfoResponse;
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
}
