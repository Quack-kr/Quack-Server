package org.quack.QUACKServer.global.common.constant;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public interface QuackCode {
    HttpStatus getHttpStatus();

    String getDescription();

    String getCode();


    @Getter
    enum ExceptionCode implements QuackCode {

        // Error 별 응답 코드 작성


        // 500 ERROR
        SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 오류");

        final HttpStatus httpStatus;
        final String description;

        ExceptionCode(HttpStatus httpStatus, String description) {
            this.httpStatus = httpStatus;
            this.description = description;
        }

        public String getCode(){
            return name();
        }

    }


}
