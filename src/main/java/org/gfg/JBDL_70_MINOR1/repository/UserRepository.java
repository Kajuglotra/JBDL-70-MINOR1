package org.gfg.JBDL_70_MINOR1.repository;

import org.gfg.JBDL_70_MINOR1.model.User;
import org.gfg.JBDL_70_MINOR1.model.UserType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user where :query", nativeQuery = true)
    List<User> findUsersByNativeQuery(@Param("query") String q);

    User findByPhoneNoAndUserType(String phoneNo, UserType type);
    User findByEmail(String email);
}
