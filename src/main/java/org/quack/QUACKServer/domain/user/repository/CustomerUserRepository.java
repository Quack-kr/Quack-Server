package org.quack.QUACKServer.domain.user.repository;

import org.quack.QUACKServer.domain.user.domain.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerUserRepository extends JpaRepository<CustomerUser, Long> {

    Optional<CustomerUser> findByProviderId(String providerId);

    Optional<CustomerUser> findByNickname(String nickname);

}
