package org.quack.QUACKServer.common.domain;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name="last_modified_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
