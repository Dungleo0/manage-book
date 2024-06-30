package com.example.app.entity;


import com.example.app.customenum.RoleEnum;
import com.example.app.customenum.UserTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tbl_user")
public class UserEntity extends SupperEntity {

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserTypeEnum type;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "card_id_number", unique = true)
    private String cardIdNumber;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;
}