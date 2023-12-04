package com.example.kafkastream.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Invoice{
    private String invoice;
    private String id;
}
