package com.example.kafkastream;

import com.example.kafkastream.aop.Logging;
import com.example.kafkastream.model.User;
import com.example.kafkastream.repository.UserRepository;
import org.aspectj.lang.annotation.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service {

    @Autowired
    UserRepository userRepository;

    @Logging(showResult = true,showArgs = true)
    public User getUser() {
        return userRepository.getReferenceById(1L);
    }
}
