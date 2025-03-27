package com.tms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Scope("prototype")
@Component
@Entity(name = "product")
public class Product {
    @Id
    @SequenceGenerator(name = "prod_seq_gen", sequenceName = "product_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "prod_seq_gen")
    private Integer id;
    private String name;
    private Double price;
    private Timestamp created;
    private Timestamp updated;
}
