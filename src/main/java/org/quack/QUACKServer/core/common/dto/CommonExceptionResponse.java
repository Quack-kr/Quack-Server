package org.quack.QUACKServer.core.common.dto;

import org.quack.QUACKServer.core.common.constant.ErrorCode;

public record CommonExceptionResponse(ErrorCode code, String customMessaged) {
}
