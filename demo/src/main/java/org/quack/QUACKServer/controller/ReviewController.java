package org.quack.QUACKServer.controller;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.service.ReviewService;
import org.quack.QUACKServer.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

}
