package org.gfg.JBDL_70_MINOR1.repository;

import org.gfg.JBDL_70_MINOR1.model.Book;
import org.gfg.JBDL_70_MINOR1.model.BookType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitle(String title);

    List<Book> findByTitleContaining(String title);

    List<Book> findByBookType(BookType bookType);

    List<Book> findByBookNo(String bookNo);
}
