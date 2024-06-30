package com.example.app.controller;


import com.example.app.dto.api.ResponseDto;
import com.example.app.dto.auth.input.LoginInput;
import com.example.app.dto.auth.input.SignUpInput;

import com.example.app.dto.auth.output.LoginOutput;
import com.example.app.dto.auth.output.SignUpOutput;
import com.example.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseDto<LoginOutput> login(@RequestBody LoginInput input) {
        return ResponseDto.ok(authService.login(input));
    }

    @PostMapping("sign-up")
    public ResponseDto<SignUpOutput> signUp(@RequestBody SignUpInput input) {
        return ResponseDto.ok(authService.signUp(input));
    }

}
