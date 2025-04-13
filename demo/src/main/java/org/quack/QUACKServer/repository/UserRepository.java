package org.quack.QUACKServer.repository;

import org.quack.QUACKServer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findBySocialTypeAndSocialId(String socialType, Long socialId);

    boolean existsByNickname(String nickname);
}
