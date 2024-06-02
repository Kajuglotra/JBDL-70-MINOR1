package org.gfg.JBDL_70_MINOR1.service;

import jakarta.transaction.Transactional;
import lombok.Setter;
import org.gfg.JBDL_70_MINOR1.dto.TxnRequest;
import org.gfg.JBDL_70_MINOR1.exception.TxnException;
import org.gfg.JBDL_70_MINOR1.model.*;
import org.gfg.JBDL_70_MINOR1.repository.TxnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private TxnRepository txnRepository;

    @Value("${book.valid.upto}")
    private String validDays;

    @Value("${book.fine.amount.per.day}")
    private String finePerDay;

    public User getUserFromDB(TxnRequest txnRequest) throws TxnException {
        // student is correct ?
        User userFromDb = userService.getStudentByPhoneNo(txnRequest.getUserPhoneNo());
        if(userFromDb == null){
            throw new TxnException("Student does not belong to my library");
        }
        return userFromDb;
    }

    public Book getBookFromDB(TxnRequest txnRequest) throws TxnException {
        List<Book> books = bookService.filter(BookFilterType.BOOK_NO, Operator.EQUALS, txnRequest.getBookNo());
        if(books.isEmpty()){
            throw new TxnException("Book does not belong to my library");
        }
        Book bookFromDb = books.get(0);
        return bookFromDb;
    }

    @Transactional(rollbackOn = {TxnException.class})
    public String createTxn(User userFromDb,  Book bookFromDb){
        String txnId = UUID.randomUUID().toString();
        Txn txn = Txn.builder().
                txnId(txnId).
                user(userFromDb).
                book(bookFromDb).
                txnStatus(TxnStatus.ISSUED).
                build();
        txnRepository.save(txn);
        bookFromDb.setUser(userFromDb);
        bookService.updateBookData(bookFromDb);
        return txnId;
    }

    public String create(TxnRequest txnRequest) throws TxnException {
        User userFromDb = getUserFromDB(txnRequest);
        Book bookFromDb = getBookFromDB(txnRequest);
        if (bookFromDb.getUser() != null) {
            throw new TxnException("Book already has been issued to someone else");
        }
        return createTxn(userFromDb, bookFromDb);
    }
    @Transactional(rollbackOn = {TxnException.class})
    public int returnBook(TxnRequest txnRequest) throws TxnException {
        User userFromDb =  getUserFromDB(txnRequest);
        Book bookFromDb = getBookFromDB(txnRequest);
        if(bookFromDb.getUser() != userFromDb){
            throw new TxnException("this is not the user which the book was assigned to");
        }
        Txn txn = txnRepository.findByUserPhoneNoAndBookBookNoAndTxnStatus(txnRequest.getUserPhoneNo(), txnRequest.getBookNo(), TxnStatus.ISSUED);
        int fine = calculateFine(txn, bookFromDb.getSecurityAmount());
        if(fine == bookFromDb.getSecurityAmount()){
            txn.setTxnStatus(TxnStatus.RETURNED);
        }else {
            txn.setTxnStatus(TxnStatus.FINED);
        }
        txn.setSettlementAmount(fine);
        bookFromDb.setUser(null);
        bookService.updateBookData(bookFromDb);
        return fine;
    }

    private int calculateFine(Txn txn, int securityAmount) {
        long issueDate = txn.getCreatedOn().getTime();
        long returnDate = System.currentTimeMillis();
        long timeDiff = returnDate-issueDate;
        int daysPassed = (int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        if(daysPassed > Integer.valueOf(validDays)){
            int fineAmount  = (daysPassed-Integer.valueOf(validDays))*Integer.valueOf(finePerDay);
            return securityAmount-fineAmount;
        }
        return securityAmount;
    }
}
