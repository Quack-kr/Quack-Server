package org.quack.QUACKServer.global.common.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.common.dto
 * @fileName : PageInfo
 * @date : 25. 5. 3.
 */

@Builder(access = AccessLevel.PRIVATE)
public record PageInfo(int pageNo, int pageSize) {

    public static Pageable toPageable(PageInfo pageInfo) {
        return PageRequest.of(pageInfo.pageNo(), pageInfo.pageSize());
    }
}
