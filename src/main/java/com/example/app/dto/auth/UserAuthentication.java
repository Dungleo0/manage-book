package com.example.app.dto.auth;

import com.example.app.customenum.RoleEnum;
import com.example.app.customenum.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthentication {
    private Long userId;
    private RoleEnum role;
    private UserTypeEnum type;
}
