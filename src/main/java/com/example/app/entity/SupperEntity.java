package com.example.app.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class SupperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created_data")
    private LocalDateTime createDate;

    @Column(name = "updated_data")
    private LocalDateTime updateDate;

    @PrePersist
    private void preCreate() {
        createDate = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        updateDate = LocalDateTime.now();
    }
}
