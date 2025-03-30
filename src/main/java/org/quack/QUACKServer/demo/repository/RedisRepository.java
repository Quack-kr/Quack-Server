package org.quack.QUACKServer.demo.repository;


import org.quack.QUACKServer.demo.domain.Redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<RefreshToken, Long> {

}
