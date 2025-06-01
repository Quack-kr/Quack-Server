package org.quack.QUACKServer.menu.repository;

import org.quack.QUACKServer.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryCustom {
}
