package org.quack.QUACKServer.global.common.dto;

import org.quack.QUACKServer.global.common.constans.QuackCode;
import org.springframework.util.StringUtils;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.common.exception
 * @fileName : CommonExceptionResponse
 * @date : 25. 3. 29.
 */
public record CommonExceptionResponse(QuackCode quackCode, String customMessage) {


    public CommonExceptionResponse(QuackCode quackCode, String customMessage) {

        this.quackCode = quackCode;

        if (!StringUtils.hasText(customMessage)) {
            this.customMessage = quackCode.getDescription();
        } else {
            this.customMessage = customMessage;
        }

    }
}
