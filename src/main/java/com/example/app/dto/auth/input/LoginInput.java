package com.example.app.dto.auth.input;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInput {
    private String username;
    private String password;
}
