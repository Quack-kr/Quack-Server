package org.quack.QUACKServer.domain.user.repository;

import org.quack.QUACKServer.domain.auth.enums.AuthEnum;
import org.quack.QUACKServer.domain.user.domain.CustomerUserNicknameSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.repository
 * @fileName : NicknameSequenceRepository
 * @date : 25. 5. 31.
 */
@Repository
public interface NicknameSequenceRepository extends JpaRepository<CustomerUserNicknameSequence, Long> {

    @Query("""
        SELECT COALESCE(MAX(n.sequence), 0) + 1
        FROM CustomerUserNicknameSequence n
        WHERE n.colorPrefix = :colorPrefix AND n.menuPrefix = :menuPrefix
    """)
    Long findMaxSequenceByPrefix(
            @Param("colorPrefix") AuthEnum.NicknameColorPrefix colorPrefix,
            @Param("menuPrefix") AuthEnum.NicknameMenuPrefix menuPrefix
    );

    Optional<CustomerUserNicknameSequence> findByColorPrefixAndMenuPrefix(
            AuthEnum.NicknameColorPrefix colorPrefix,
            AuthEnum.NicknameMenuPrefix menuPrefix
    );


}
