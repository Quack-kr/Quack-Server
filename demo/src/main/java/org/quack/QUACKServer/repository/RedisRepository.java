package org.quack.QUACKServer.repository;


import org.quack.QUACKServer.domain.Redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<RefreshToken, Long> {

}
