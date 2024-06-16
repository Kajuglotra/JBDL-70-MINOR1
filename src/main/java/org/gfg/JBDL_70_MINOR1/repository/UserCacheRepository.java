package org.gfg.JBDL_70_MINOR1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.gfg.JBDL_70_MINOR1.model.User;

import java.util.concurrent.TimeUnit;

@Repository
public class UserCacheRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.user.details.timeout}")
    private int timeout;

    private static String userKey ="user::";


    public User getUser(String  email){
        String key = userKey+email;
        return (User) redisTemplate.opsForValue().get(key);
    }

    public void setUser(String email, User user){
        String key = userKey+email;
        redisTemplate.opsForValue().set(key, user, timeout, TimeUnit.MINUTES);
//        redisTemplate.opsForList().leftPush();
//                redisTemplate.expire(key, 60, TimeUnit.MINUTES);
    }
}
