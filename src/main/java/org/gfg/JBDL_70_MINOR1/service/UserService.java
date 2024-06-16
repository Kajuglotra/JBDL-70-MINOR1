package org.gfg.JBDL_70_MINOR1.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gfg.JBDL_70_MINOR1.dto.UserRequest;
import org.gfg.JBDL_70_MINOR1.exception.UserException;
import org.gfg.JBDL_70_MINOR1.model.Operator;
import org.gfg.JBDL_70_MINOR1.model.User;
import org.gfg.JBDL_70_MINOR1.model.UserFilterType;
import org.gfg.JBDL_70_MINOR1.model.UserType;
import org.gfg.JBDL_70_MINOR1.repository.UserCacheRepository;
import org.gfg.JBDL_70_MINOR1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private static final Log logger = LogFactory.getLog(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Value("${student.authority}")
    private String studentAuthority;

    @Value("${admin.authority}")
    private String adminAuthority;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserCacheRepository cacheRepository;

    public User addStudent(UserRequest userRequest) {
        User user = userRequest.toUser();
        user.setAuthorities(studentAuthority);
        user.setPassword(encoder.encode(userRequest.getPassword()));
        user.setUserType(UserType.STUDENT);
        return userRepository.save(user);
    }

    public List<User> filter(String filterBy, String operator, String value) {
        String[] filters = filterBy.split(",");
        String[] operators = operator.split(",");
        String[] values = value.split(",");
        StringBuilder query = new StringBuilder();
        query.append("select * from user where ");
        for(int i = 0 ; i< operators.length; i++){
            UserFilterType userFilterType = UserFilterType.valueOf(filters[i]);
            Operator operator1 = Operator.valueOf(operators[i]);
            String finalValue = values[i];
            query = query.append(userFilterType).
                    append(operator1.getValue()).
                    append("'").append(finalValue).
                    append("'").append(" and ");
        }
        logger.info("query is :" + query.substring(0, query.length()-4));
        Query query1 = em.createNativeQuery(query.substring(0, query.length()-4), User.class);
        return query1.getResultList();
    }

    public User getStudentByPhoneNo(String userPhoneNo) {
        return userRepository.findByPhoneNoAndUserType(userPhoneNo, UserType.STUDENT);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       // load this user from redis first
       // if the user is present in redis, I want to get the data from redis

       // if teh data is not present in redis, i want togo to db
       // i will be checking at db

       // if the data is present at db, then i will be keeping that data in my cache as well?

        User user = cacheRepository.getUser(email);
        if(user != null){
            return user;
        }
        user =  userRepository.findByEmail(email);
        if(user == null){
            new UserException("The user you are looking for does not belong to the library");
        }
        cacheRepository.setUser(email, user);
        return user;
    }

    public User addAdmin(UserRequest userRequest) {
        User user = userRequest.toUser();
        user.setAuthorities(adminAuthority);
        user.setPassword(encoder.encode(userRequest.getPassword()));
        user.setUserType(UserType.ADMIN);
        return userRepository.save(user);
    }
}
