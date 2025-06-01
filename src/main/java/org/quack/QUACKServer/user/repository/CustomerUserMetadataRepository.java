package org.quack.QUACKServer.user.repository;

import org.quack.QUACKServer.user.domain.CustomerUserMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerUserMetadataRepository extends JpaRepository<CustomerUserMetadata, Long> {

    Optional<CustomerUserMetadata> findByCustomerUserId(Long customerUserId);
}