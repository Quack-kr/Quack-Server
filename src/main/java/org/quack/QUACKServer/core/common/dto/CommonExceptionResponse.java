package org.quack.QUACKServer.core.common.dto;

import org.quack.QUACKServer.core.error.constant.ErrorCode;

public record CommonExceptionResponse(ErrorCode code, String customMessaged) {
}
