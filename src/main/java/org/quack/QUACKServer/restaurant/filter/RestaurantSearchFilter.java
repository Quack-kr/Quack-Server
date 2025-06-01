package org.quack.QUACKServer.restaurant.filter;

import lombok.Builder;
import lombok.Getter;
import org.quack.QUACKServer.auth.domain.PrincipalManager;
import org.quack.QUACKServer.restaurant.dto.request.SearchRestaurantsByKeywordRequest;
import org.quack.QUACKServer.restaurant.enums.RestaurantEnum;
import org.quack.QUACKServer.core.common.dto.PageInfo;
import org.springframework.data.domain.Pageable;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.filter
 * @fileName : RestaurantSearchFilter
 * @date : 25. 5. 24.
 */

@Builder
@Getter
public class RestaurantSearchFilter {

    private Double longitude;
    private Double latitude;
    private String keyword;
    private Pageable pageable;
    private RestaurantEnum.RestaurantSortType sortType;
    private Long customerUserId;
    private boolean isOpen;

    public static RestaurantSearchFilter from(SearchRestaurantsByKeywordRequest request) {
        return RestaurantSearchFilter.builder()
                .longitude(request.userLocation().longitude())
                .latitude(request.userLocation().latitude())
                .keyword(request.keyword())
                .sortType(request.sort().sortType())
                .isOpen(request.sort().isOpen())
                .pageable(PageInfo.toPageable(request.pageInfo()))
                .customerUserId(PrincipalManager.getCustomerUserId())
                .build();
    }
}
