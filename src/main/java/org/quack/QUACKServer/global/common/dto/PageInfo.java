package org.quack.QUACKServer.global.common.dto;

import lombok.AccessLevel;
import lombok.Builder;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.common.dto
 * @fileName : PageInfo
 * @date : 25. 5. 3.
 */

@Builder(access = AccessLevel.PRIVATE)
public record PageInfo(int pageNo, int pageSize, int totalElements, int totalPages, boolean last) {

}
