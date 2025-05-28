package org.quack.QUACKServer.domain.menu.repository;

import org.quack.QUACKServer.domain.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryCustom {
}
