package org.quack.QUACKServer.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.user.domain.User;
import org.quack.QUACKServer.domain.user.repository.UserRepository;
import org.quack.QUACKServer.global.common.dto.SocialAuthDto;
import org.quack.QUACKServer.global.security.enums.ClientType;
import org.quack.QUACKServer.global.security.jwt.JwtProvider;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String socialId) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findBySocialId(socialId);

        if(user.isPresent()) {
            return QuackUser.from(user.get());
        } else {
            return QuackUser.empty(socialId);
        }
    }

    public void createBeforeSignUp (ClientType clientType, SocialAuthDto socialAuthDto) {
        User user = User.createBySocial(
                clientType, socialAuthDto.getProviderId(), socialAuthDto.getEmail(), "", "" );

        userRepository.save(user);
    }

}
