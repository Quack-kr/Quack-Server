package org.quack.QUACKServer.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.global.security.enums.ClientType;
import org.quack.QUACKServer.global.security.exception.BeforeSignUpException;
import org.quack.QUACKServer.global.security.provider.LoginAuthenticationProvider;
import org.quack.QUACKServer.global.security.provider.LoginAuthenticationProviderFactory;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.security.filter
 * @fileName : QuackLoginFilter
 * @date : 25. 4. 15.
 */

@Slf4j
@Component
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private final LoginAuthenticationProviderFactory providerFactory;

    public LoginFilter(AuthenticationManager authenticationManager, LoginAuthenticationProviderFactory providerFactory) {
        super(new AntPathRequestMatcher("/api/v1/auth/login", "POST"));
        this.providerFactory = providerFactory;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        try {
            ClientType clientType = ClientType.from(request.getParameter("client_type"));
            String idToken = request.getHeader("id_token");

            if (clientType == null || idToken == null) {
                throw new AuthenticationException("Invalid client_type or id_token") {};
            }

            LoginAuthenticationProvider provider = providerFactory.get(clientType);

            // TODO : 네이버 에서는 토큰이 없기 때문에 여기서도 메소드 내부적으로 구현 해야함 우선 애플만.
            QuackAuthenticationToken quackAuthenticationToken = new QuackAuthenticationToken(clientType, "", idToken);

            return provider.authenticate(quackAuthenticationToken);


        } catch (AuthenticationException e) {
            log.error("Authentication failed: ", e);
            throw e;  // 예외를 다시 던져서 스프링 시큐리티가 처리하도록 함
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        if (failed instanceof BeforeSignUpException beforeSignUpException) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    Map.of("message", beforeSignUpException.getMessage(),
                            "email", beforeSignUpException.getSocialAuthDto().getEmail(),
                            "status", "BEFORE_SIGNUP" )
            ));
            return;
        }

        // 기본 실패 처리
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
