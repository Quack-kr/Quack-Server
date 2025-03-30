package org.quack.QUACKServer.demo.controller;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.demo.domain.Review;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record UserReviewsResponse(

) {

    public static UserReviewsResponse from(List<Review> reviews) {
        return UserReviewsResponse.builder().build();
    }
}
