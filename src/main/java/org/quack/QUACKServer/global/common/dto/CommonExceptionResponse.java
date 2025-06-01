package org.quack.QUACKServer.global.common.dto;

import org.quack.QUACKServer.global.common.constant.QuackCode.ExceptionCode;

public record CommonExceptionResponse(ExceptionCode exceptionCode, String customMessaged) {
}
