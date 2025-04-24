package org.quack.QUACKServer.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.enums
 * @fileName : SignUpStatus
 * @date : 25. 4. 20.
 */

@Getter
@AllArgsConstructor
public enum SignUpStatus {

    BEFORE("BEFORE"),
    FINISHED("FINISHED");
    private final String value;

}
