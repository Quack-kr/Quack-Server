package org.quack.QUACKServer.domain.user.repository;

import org.quack.QUACKServer.domain.user.domain.CustomerUserMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerUserMetadataRepository extends JpaRepository<CustomerUserMetadata, Long> {

    Optional<CustomerUserMetadata> findByCustomerUserId(Long customerUserId);
}