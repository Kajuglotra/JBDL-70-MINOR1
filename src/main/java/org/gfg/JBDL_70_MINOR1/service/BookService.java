package org.gfg.JBDL_70_MINOR1.service;

import org.gfg.JBDL_70_MINOR1.dto.BookRequest;
import org.gfg.JBDL_70_MINOR1.model.Author;
import org.gfg.JBDL_70_MINOR1.model.Book;
import org.gfg.JBDL_70_MINOR1.repository.AuthorRepository;
import org.gfg.JBDL_70_MINOR1.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(BookRequest bookRequest) {

        Author authorFromDB = authorRepository.getAuthorByEmail(bookRequest.getAuthorEmail());
        if(authorFromDB == null){
            // object of Author table
            // save the data in author table ?
            authorFromDB = authorRepository.save(bookRequest.toAuthor());
        }
        Book book = bookRequest.toBook();
        book.setAuthor(authorFromDB);
        return bookRepository.save(book);
    }
}
