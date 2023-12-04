package com.example.kafkastream.kafka;

import com.example.kafkastream.aop.Logging;
import com.example.kafkastream.dto.UserDto;
import com.example.kafkastream.mapper.UserMapper;
import com.example.kafkastream.model.User;
import com.example.kafkastream.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.function.Consumer;

@Configuration
public class UserConsumer {
    Logger logger = LoggerFactory.getLogger(UserConsumer.class);

    private final UserRepository userRepository;

    public UserConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   @Bean
   @Logging
    Consumer<UserDto> userDtoConsumer() {
        return it -> {
            logger.info("successfully started");
            User user = UserMapper.INSTANCE.userDtoToUser(it);
            user.setId(new Random().nextLong());
            user.setName(user.getName() + "kafka consumer ok");
            user.setPassword("1");
            user.setAddress("1");
            userRepository.save(user);
        };
    }

}
