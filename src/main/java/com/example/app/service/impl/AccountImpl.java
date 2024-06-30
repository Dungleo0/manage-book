package com.example.app.service.impl;

import com.example.app.dto.account.output.GetPersonalInfo;
import com.example.app.entity.UserEntity;
import com.example.app.exception.BusinessException;
import com.example.app.mapper.UserMapper;
import com.example.app.repository.UserRepository;
import com.example.app.service.AccountService;
import com.example.app.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountImpl implements AccountService {


    private UserRepository userRepository;

    public AccountImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public GetPersonalInfo getPersonalInfo() {
        UserEntity userFound = userRepository.findById(AuthUtil.getUserId())
                .orElseThrow(() -> new BusinessException("USER_NOT_FOUND", "Thông tin cá nhân không được tìm thấy"));

        return GetPersonalInfo.builder()
                .username(userFound.getUsername())
                .name(userFound.getName())
                .cardIdNumber(userFound.getCardIdNumber())
                .type(userFound.getType())
                .role(userFound.getRole())
                .build();
    }
}
