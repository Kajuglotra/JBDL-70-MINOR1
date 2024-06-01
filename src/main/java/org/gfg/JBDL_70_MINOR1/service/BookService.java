package org.gfg.JBDL_70_MINOR1.service;

import org.gfg.JBDL_70_MINOR1.dto.BookRequest;
import org.gfg.JBDL_70_MINOR1.model.*;
import org.gfg.JBDL_70_MINOR1.repository.AuthorRepository;
import org.gfg.JBDL_70_MINOR1.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Book> filter(BookFilterType bookFilterType, Operator operator, String value) {
        switch (bookFilterType){
            case BOOK_TITLE :
                switch (operator){
                    case EQUALS :
                        return bookRepository.findByTitle(value);
                    case LIKE:
                        return bookRepository.findByTitleContaining(value);
                    default:
                        return new ArrayList<>();
                }
            case BOOK_TYPE:
                switch (operator) {
                    case EQUALS:
                        return bookRepository.findByBookType(BookType.valueOf(value));
                }
            case BOOK_NO:
                switch (operator) {
                    case EQUALS:
                        return bookRepository.findByBookNo(value);
                }

        }
        return new ArrayList<>();
    }

    public void updateBookData(Book bookFromDb) {
        bookRepository.save(bookFromDb);
    }
}
