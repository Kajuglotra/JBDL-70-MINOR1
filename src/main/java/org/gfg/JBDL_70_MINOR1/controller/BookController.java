package org.gfg.JBDL_70_MINOR1.controller;

import jakarta.validation.Valid;
import org.gfg.JBDL_70_MINOR1.dto.BookRequest;
import org.gfg.JBDL_70_MINOR1.model.Book;
import org.gfg.JBDL_70_MINOR1.model.BookFilterType;
import org.gfg.JBDL_70_MINOR1.model.Operator;
import org.gfg.JBDL_70_MINOR1.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public Book addBook(@RequestBody @Valid BookRequest bookRequest){

        // validations before the business logic?
//        if(StringUtil.isNullOrEmpty(bookRequest.getBookNo())){
//            throw new Exception("book no should not be blank ");
//        }

        // to call the business logic
        Book book = bookService.addBook(bookRequest);

        // return the accurate/ required data
        return book;

    }

    @GetMapping("/filter")
    public List<Book> filter(@RequestParam("filterBy") BookFilterType bookFilterType,
                                  @RequestParam("operator")Operator operator,
                                  @RequestParam("value") String value){

        return bookService.filter(bookFilterType, operator, value);
    }




}
// creating a row in book table
// u have to insert the information related to author who has written this


// kathy -> introduction to java
// kathy -> advanced Java


// curd
