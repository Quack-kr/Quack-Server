package org.quack.QUACKServer.user.repository;

import org.quack.QUACKServer.user.domain.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerUserRepository extends JpaRepository<CustomerUser, Long> {

    Optional<CustomerUser> findByProviderId(String providerId);

    @Query("SELECT EXISTS (SELECT cu FROM CustomerUser cu WHERE cu.isDeleted = false AND cu.nickname = :nickname)")
    boolean existsByNickname(@Param("nickname") String nickname);

}
