package com.example.app.dto.auth.input;


import com.example.app.customenum.RoleEnum;
import com.example.app.customenum.UserTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignUpInput {


    private String username;

    private String password;

    private String name;

    private LocalDate birthday;

    private String cardIdNumber;

    private String phoneNumber;


}
