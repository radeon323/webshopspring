package com.luxoft.oleksandr_shevchenko.webshop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Builder
public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime creationDate;
}
