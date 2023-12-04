package com.example.kafkastream.repository;

import com.example.kafkastream.model.PaymentRedis;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.redis.repository.cdi.RedisRepositoryBean;


public interface PaymentRedisRepository extends KeyValueRepository<PaymentRedis, String> {

}
