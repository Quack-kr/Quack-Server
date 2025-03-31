package org.quack.QUACKServer.repository;

import org.quack.QUACKServer.domain.Inquiry;
import org.quack.QUACKServer.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.repository
 * @fileName : KeywordRepository
 * @date : 25. 3. 31.
 */
@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

}
