package org.gfg.JBDL_70_MINOR1.repository;

import org.gfg.JBDL_70_MINOR1.model.Txn;
import org.gfg.JBDL_70_MINOR1.model.TxnStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn, Integer> {

    Txn findByUserPhoneNoAndBookBookNoAndTxnStatus(String phoneNo, String bookNo, TxnStatus status);
}
