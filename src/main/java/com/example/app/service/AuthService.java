package com.example.app.service;

import com.example.app.dto.auth.input.LoginInput;
import com.example.app.dto.auth.input.SignUpInput;
import com.example.app.dto.auth.output.LoginOutput;
import com.example.app.dto.auth.output.SignUpOutput;

public interface AuthService {
    SignUpOutput signUp(SignUpInput input);

    LoginOutput login(LoginInput input);


}
