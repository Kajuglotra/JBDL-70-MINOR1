package org.gfg.JBDL_70_MINOR1;

import org.gfg.JBDL_70_MINOR1.repository.TxnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Jbdl70Minor1Application implements CommandLineRunner {

	@Autowired
	private TxnRepository txnRepository;

	public static void main(String[] args) {
		SpringApplication.run(Jbdl70Minor1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		txnRepository.updateExistingTxn();
		txnRepository.updateBook();
		System.out.println("My application has started !!");
	}
}
