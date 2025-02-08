package org.quack.QUACKServer.repository;

import org.quack.QUACKServer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndProviderType(String email, String providerType);

    boolean existsByNickname(String nickname);
}
