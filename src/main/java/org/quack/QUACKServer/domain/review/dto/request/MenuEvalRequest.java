package org.quack.QUACKServer.domain.review.dto.request;

import org.quack.QUACKServer.domain.menu.enums.MenuEnum.MenuEvalType;

public record MenuEvalRequest(
        Long menuId,
        MenuEvalType evalType
) { }
