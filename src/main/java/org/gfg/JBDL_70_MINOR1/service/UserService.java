package org.gfg.JBDL_70_MINOR1.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gfg.JBDL_70_MINOR1.dto.UserRequest;
import org.gfg.JBDL_70_MINOR1.model.Operator;
import org.gfg.JBDL_70_MINOR1.model.User;
import org.gfg.JBDL_70_MINOR1.model.UserFilterType;
import org.gfg.JBDL_70_MINOR1.model.UserType;
import org.gfg.JBDL_70_MINOR1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Log logger = LogFactory.getLog(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    public User addStudent(UserRequest userRequest) {
        User user = userRequest.toUser();
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
//        return userRepository.findUsersByNativeQuery(query.substring(0, query.length()-4).toString());
    }

    public User getStudentByPhoneNo(String userPhoneNo) {
        return userRepository.findByPhoneNoAndUserType(userPhoneNo, UserType.STUDENT);
    }
}
