package org.gfg.JBDL_70_MINOR1.repository;

import jakarta.transaction.Transactional;
import org.gfg.JBDL_70_MINOR1.model.Txn;
import org.gfg.JBDL_70_MINOR1.model.TxnStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TxnRepository extends JpaRepository<Txn, Integer> {

    Txn findByUserPhoneNoAndBookBookNoAndTxnStatus(String phoneNo, String bookNo, TxnStatus status);

    @Transactional
    @Modifying
    @Query(value =  "update txn set created_on = '2024-05-01 17:11:07.756000' , settlement_amount=0 ,  txn_status=0  where id =1 ", nativeQuery = true)
    void updateExistingTxn();


    @Transactional
    @Modifying
    @Query(value = "update book set user_id = 2 where id =1;" , nativeQuery = true)
    void updateBook();
}
