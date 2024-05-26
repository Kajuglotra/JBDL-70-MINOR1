package org.gfg.JBDL_70_MINOR1.service;

import org.gfg.JBDL_70_MINOR1.model.Author;
import org.gfg.JBDL_70_MINOR1.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author getAuthorData(String email) {
        return  authorRepository.getAuthorByEmail(email);
    }
}
