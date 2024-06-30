package com.example.app.controller;


import com.example.app.dto.account.output.GetPersonalInfo;
import com.example.app.dto.api.ResponseDto;
import com.example.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/personal-info")
    public ResponseDto<GetPersonalInfo> getPersonalInfo() {
        return ResponseDto.ok(accountService.getPersonalInfo());
    }
}
