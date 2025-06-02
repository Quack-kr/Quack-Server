package org.quack.QUACKServer.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    FINISH("FINISH"),
    REFRESH("REFRESH");
    private final String value;

}
