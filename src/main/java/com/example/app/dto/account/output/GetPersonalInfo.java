package com.example.app.dto.account.output;


import com.example.app.customenum.RoleEnum;
import com.example.app.customenum.UserTypeEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPersonalInfo {
    private String username;
    private String name;
    private UserTypeEnum type;
    private LocalDate birthday;
    private String cardIdNumber;
    private String phoneNumber;
    private RoleEnum role;

}
