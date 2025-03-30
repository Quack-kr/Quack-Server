package org.quack.QUACKServer.demo.controller;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.demo.service.ReviewService;
import org.quack.QUACKServer.demo.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

}
