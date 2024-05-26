package org.gfg.JBDL_70_MINOR1.repository;

import org.gfg.JBDL_70_MINOR1.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    // native Query
    @Query(value = "select * from author where email = :email", nativeQuery = true)
    Author getAuthorByEmail(String email);
}
