package org.quack.QUACKServer.core.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.quack.QUACKServer.core.security.enums.ProviderType;
import org.quack.QUACKServer.core.security.exception.BeforeSignUpException;
import org.quack.QUACKServer.core.security.jwt.JwtUtil;
import org.quack.QUACKServer.core.security.provider.LoginAuthenticationProvider;
import org.quack.QUACKServer.core.security.provider.LoginAuthenticationProviderFactory;
import org.quack.QUACKServer.auth.domain.QuackAuthenticationDto;
import org.quack.QUACKServer.auth.dto.response.AuthResponse;
import org.quack.QUACKServer.auth.dto.response.BeforeSignUpResponse;
import org.quack.QUACKServer.auth.enums.SignUpStatus;
import org.quack.QUACKServer.user.service.CustomerUserService;
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
    private final JwtUtil jwtUtil;
    private final CustomerUserService customerUserService;

    public SocialAuthLoginFilter(AuthenticationManager authenticationManager, LoginAuthenticationProviderFactory providerFactory, ObjectMapper objectMapper, JwtUtil jwtUtil, CustomerUserService customerUserService) {
        super(new AntPathRequestMatcher("/v1/auth/login", "POST"));
        this.providerFactory = providerFactory;
        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.customerUserService = customerUserService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            ProviderType clientType = ProviderType.from(request.getParameter("client_type"));
            String idToken = request.getHeader("id_token");

            if (clientType == null || idToken == null) {
                throw new CommonException(ErrorCode.INVALID_ID_TOKEN);
            }

            LoginAuthenticationProvider provider = providerFactory.get(clientType);

            QuackAuthenticationDto quackAuthenticationDto = new QuackAuthenticationDto(clientType, "", idToken);

            return provider.authenticate(quackAuthenticationDto);

        } catch (AuthenticationException e) {
            throw new CommonException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        if (failed instanceof BeforeSignUpException beforeSignUpException) {

            BeforeSignUpResponse beforeSignUpResponse = BeforeSignUpResponse.builder()
                    .signUpStatus(SignUpStatus.BEFORE)
                    .email(beforeSignUpException.getCustomerUserInfo().getEmail())
                    .nickname(customerUserService.generateNickname())
                    .build();

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ResponseDto.success(beforeSignUpResponse)));

            return;
        }

        super.unsuccessfulAuthentication(request, response, failed);
    }

    // 인증 성공
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        CustomerUserInfo customerUserInfo = (CustomerUserInfo) authResult.getDetails();
        AuthResponse authResponse = AuthResponse.from(jwtUtil.generateToken(customerUserInfo.getEmail(), customerUserInfo.getCustomerUserId()));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(
                    objectMapper.writeValueAsString(ResponseDto.successCreate(authResponse))
                );
    }
}



