package com.example.kafkastream.rest;

import com.example.kafkastream.Service;
import com.example.kafkastream.aop.Logging;
import com.example.kafkastream.dto.UserDto;
import com.example.kafkastream.model.Invoice;
import com.example.kafkastream.model.PaymentRedis;
import com.example.kafkastream.model.User;
import com.example.kafkastream.repository.InvoiceMongoRepository;
import com.example.kafkastream.repository.PaymentRedisRepository;
import com.example.kafkastream.repository.UserRepository;
import io.micrometer.core.annotation.Timed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestControllerTest {

    private final UserRepository userRepository;
    private final StreamBridge streamBridge;
    private final PaymentRedisRepository paymentRedisRepository;
    private final InvoiceMongoRepository mongoRepository;

    private final Service service;


    public RestControllerTest(UserRepository userRepository, StreamBridge streamBridge, PaymentRedisRepository paymentRedisRepository, InvoiceMongoRepository mongoRepository, Service service) {
        this.userRepository = userRepository;
        this.streamBridge = streamBridge;
        this.paymentRedisRepository = paymentRedisRepository;
        this.mongoRepository = mongoRepository;
        this.service = service;
    }

    @GetMapping
    @Logging(showArgs = true,showResult = true)
    public ResponseEntity<User> sendToKafka(@RequestBody User user , @RequestParam List<String> strings) throws Exception {

        Message<UserDto> messageBuilder1 = MessageBuilder.withPayload(new UserDto("1",1,"1","1")).setHeader("partitionKey",1).build();
        Message<UserDto> messageBuilder2 = MessageBuilder.withPayload(new UserDto("2",2,"2","2")).setHeader("partitionKey", 2).build();
        Message<UserDto> messageBuilder3 = MessageBuilder.withPayload(new UserDto("3",3,"3","3")).setHeader("partitionKey",3).build();
        streamBridge.send("userSupplier-out-0",messageBuilder1);
        streamBridge.send("userSupplier-out-0",messageBuilder2);
        streamBridge.send("userSupplier-out-0",messageBuilder3);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @Timed
    @GetMapping("/redis/{id}")
    public ResponseEntity<String> sendToRedis(@PathVariable("id") Long payId) throws Exception {
        paymentRedisRepository.save(new PaymentRedis("redis-test", "1"));
        paymentRedisRepository.save(new PaymentRedis("redis-test", "2"));
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }

    @GetMapping("/redis/set/{id}")
    public ResponseEntity<String> setToRedis(@PathVariable("id") Long payId) throws Exception {
        System.out.println(paymentRedisRepository.save(new PaymentRedis("redis-test", "1")));
        System.out.println(paymentRedisRepository.save(new PaymentRedis("redis-test", "2")));
        System.out.println(paymentRedisRepository.save(new PaymentRedis("redis-test", "3")));
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }

    @GetMapping("/redis/get/{id}")
    public ResponseEntity<String> getToRedis(@PathVariable("id") Long payId) throws Exception {
        System.out.println(paymentRedisRepository.findById("1"));
        System.out.println(paymentRedisRepository.findById("2"));
        System.out.println(paymentRedisRepository.findById("3"));
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }

    @GetMapping("/mongo/set")
    public ResponseEntity<String> setToMongo() throws Exception {
        System.out.println(mongoRepository.save(new Invoice("invoice", "1")));
        System.out.println(mongoRepository.save(new Invoice("invoice", "2")));
        System.out.println(mongoRepository.save(new Invoice("invoice", "3")));
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }

    @GetMapping("/mongo/get")
    public ResponseEntity<String> getToMongo() throws Exception {
        System.out.println(mongoRepository.findById("1"));
        System.out.println(mongoRepository.findById("2"));
        System.out.println(mongoRepository.findById("3"));
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }

    @GetMapping("/log")
    public ResponseEntity<String> log() throws Exception {
        service.getUser();
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }
}
