package org.quack.QUACKServer.domain.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.review.dto.response.GetReviewMyCountResponse;
import org.quack.QUACKServer.domain.review.dto.response.GetMyReviewResponse;
import org.quack.QUACKServer.domain.review.service.MyPageReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.review.controller
 * @fileName : ReviewMyPageController
 * @date : 25. 5. 27.
 */
@RestController
@Slf4j
@RequestMapping(path = "")
@RequiredArgsConstructor
public class ReviewMyPageController {


    private final MyPageReviewService myPageReviewService;

    @GetMapping("/my-page/review-count")
    public GetReviewMyCountResponse selectMyPageReviewCount() {
        return myPageReviewService.getMyReviewCounts();
    }

    @GetMapping("/my-reviews")
    public GetMyReviewResponse getMyReviews(@RequestParam(defaultValue = "0") int pageNum,
                                            @RequestParam(defaultValue = "10") int sizeNum){

        return myPageReviewService.getMyReviews(pageNum, sizeNum);
    }
}
