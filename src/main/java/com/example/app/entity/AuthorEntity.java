package com.example.app.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "author")
public class AuthorEntity extends SupperEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private LocalDate birthDate;

}
