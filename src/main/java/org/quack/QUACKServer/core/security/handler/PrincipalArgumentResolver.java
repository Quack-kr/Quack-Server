package org.quack.QUACKServer.core.security.handler;

import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.core.security.handler
 * @fileName : PrincipalArgumentResolver
 * @date : 25. 6. 2.
 */
@Component
public class PrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(CustomerUserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken &&
                authentication.getPrincipal() instanceof CustomerUserInfo userInfo) {
            return userInfo;
        } else if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        } else {
            return null;
        }
    }
}
