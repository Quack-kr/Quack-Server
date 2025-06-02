package org.quack.QUACKServer.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.user.domain.CustomerUser;
import org.quack.QUACKServer.user.repository.CustomerUserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.service
 * @fileName : UserService
 * @date : 25. 4. 20.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final CustomerUserRepository customerUserRepository;

    @Override
    public CustomerUserInfo loadUserByUsername(String providerId) throws UsernameNotFoundException {

        Optional<CustomerUser> user = customerUserRepository.findByProviderId(providerId);

        if(user.isPresent()) {
            return CustomerUserInfo.from(user.get());
        } else {
            return CustomerUserInfo.empty();
        }
    }

}
