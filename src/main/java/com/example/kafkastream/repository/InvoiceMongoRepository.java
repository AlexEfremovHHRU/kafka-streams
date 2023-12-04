package com.example.kafkastream.repository;

import com.example.kafkastream.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceMongoRepository extends MongoRepository<Invoice, String> {
}
