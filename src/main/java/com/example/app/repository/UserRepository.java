package com.example.app.repository;

import com.example.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUsername(String username);

    UserEntity findFistByCardIdNumber(String cardIdNumber);

    UserEntity findFistByPhoneNumber(String phoneNumber);

}
