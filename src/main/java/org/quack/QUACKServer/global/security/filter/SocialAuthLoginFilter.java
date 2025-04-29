package org.quack.QUACKServer.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.auth.dto.response.AuthResponse;
import org.quack.QUACKServer.domain.auth.dto.response.LoginResponse;
import org.quack.QUACKServer.domain.auth.enums.SignUpStatus;
import org.quack.QUACKServer.global.common.dto.BaseResponse;
import org.quack.QUACKServer.global.security.enums.ClientType;
import org.quack.QUACKServer.global.security.exception.BeforeSignUpException;
import org.quack.QUACKServer.global.security.jwt.JwtProvider;
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

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.security.filter
 * @fileName : QuackLoginFilter
 * @date : 25. 4. 15.
 */

@Slf4j
@Component
public class SocialAuthLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final LoginAuthenticationProviderFactory providerFactory;
    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;

    public SocialAuthLoginFilter(AuthenticationManager authenticationManager, LoginAuthenticationProviderFactory providerFactory, ObjectMapper objectMapper, JwtProvider jwtProvider) {
        super(new AntPathRequestMatcher("/api/v1/auth/login", "POST"));
        this.providerFactory = providerFactory;
        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
        this.jwtProvider = jwtProvider;
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
        String path = request.getServletPath() + "/auth/login";

        if (failed instanceof BeforeSignUpException beforeSignUpException) {

            LoginResponse loginResponse = LoginResponse.builder()
                    .signUpStatus(SignUpStatus.BEFORE)
                    .email(beforeSignUpException.getQuackUser().getEmail())
                    .build();

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter()
                    .write(objectMapper.writeValueAsString(build(path, loginResponse, beforeSignUpException.getMessage(), "200")
            ));

            return;
        }

        // 기본 실패 처리
        super.unsuccessfulAuthentication(request, response, failed);
    }

    // 인증 성공
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        String path = request.getServletPath() + "/auth/login";
        QuackUser quackUser = (QuackUser) authResult.getDetails();
        AuthResponse authResponse = AuthResponse.builder()
                .signUpStatus(SignUpStatus.FINISH)
                .accessToken(jwtProvider.generateToken(quackUser))
                .refreshToken(jwtProvider.generateRefreshToken(quackUser))
                .build();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter()
                .write(objectMapper.writeValueAsString(build(path, authResponse, "인증 성공", "200")
                ));
    }

    private BaseResponse<Object> build(String path, Object data, String message, String code) {
        return BaseResponse.builder()
                .path(path)
                .data(data)
                .message(message)
                .code(code)
                .build();
    }
}



