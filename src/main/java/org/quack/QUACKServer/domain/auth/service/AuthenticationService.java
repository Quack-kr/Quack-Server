package org.quack.QUACKServer.domain.auth.service;

import org.quack.QUACKServer.demo.domain.User;
import org.quack.QUACKServer.demo.dto.auth.AuthResponse;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.service
 * @fileName : AuthenticationService
 * @date : 25. 3. 29.
 */
public interface AuthenticationService {

    AuthResponse loginUser(String token);

    void logout(User user);

    void request(Long socialId, String requestUrl);

    User attemptLogin(String token);
}
