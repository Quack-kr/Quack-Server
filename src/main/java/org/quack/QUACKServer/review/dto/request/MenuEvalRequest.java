package org.quack.QUACKServer.review.dto.request;

import org.quack.QUACKServer.menu.enums.MenuEnum.MenuEvalType;

public record MenuEvalRequest(
        Long menuId,
        MenuEvalType evalType
) { }
