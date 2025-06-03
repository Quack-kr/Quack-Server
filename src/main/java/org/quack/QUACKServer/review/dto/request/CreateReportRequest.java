package org.quack.QUACKServer.review.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.review.dto.request
 * @fileName : CreateReportRequest
 * @date : 25. 6. 2.
 */
public record CreateReportRequest(
        @NotNull Long reviewId,
        String reportContent) {
}
