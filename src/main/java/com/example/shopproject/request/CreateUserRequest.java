package com.example.shopproject.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
}
