package org.quack.QUACKServer.review.dto.request;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record CreateReviewRequest(
        List<MenuEvalRequest> menusEval,
        List<Long> negativeKeywords,
        List<Long> positiveKeywords,
        String content,
        List<MultipartFile> reviewPhotos
) { }

