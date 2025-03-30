package org.quack.QUACKServer.global.jwt;
/**
 * @author : changhyun9
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.jwt
 * @fileName : JwtValidationType
 * @date : 25. 3. 29.
 */

public enum JwtValidationType {
    ACCESS_VALID,
    REFRESH_VALID,
    ACCESS_EXPIRED,
    REFRESH_EXPIRED,
    INVALID_FORMAT_TOKEN,
    ILLEGAL_SIGNATURE_TOKEN,
    UNSUPPORTED_TOKEN,
    ILLEGAL_TOKEN

}