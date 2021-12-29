package com.luxoft.oleksandr_shevchenko.webshop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder
public class User {
    private int id;
    private String email;
    private String password;
    private String gender;
    private String firstName;
    private String lastName;
    private String about;
    private int age;

}
