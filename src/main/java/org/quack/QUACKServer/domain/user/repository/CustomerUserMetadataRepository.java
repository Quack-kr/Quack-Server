package org.quack.QUACKServer.domain.user.repository;

import org.quack.QUACKServer.domain.user.domain.CustomerUserMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerUserMetadataRepository extends JpaRepository<CustomerUserMetadata, Long> {
}