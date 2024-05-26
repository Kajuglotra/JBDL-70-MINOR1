package org.gfg.JBDL_70_MINOR1.repository;

import org.gfg.JBDL_70_MINOR1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
