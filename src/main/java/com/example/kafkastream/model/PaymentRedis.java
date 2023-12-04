package com.example.kafkastream.model;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("payment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentRedis {
    private String payment;
    private String id;

}
