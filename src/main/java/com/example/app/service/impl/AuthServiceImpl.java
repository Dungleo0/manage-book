package com.example.app.service.impl;

import com.example.app.customenum.RoleEnum;
import com.example.app.customenum.UserTypeEnum;
import com.example.app.dto.auth.UserAuthentication;
import com.example.app.dto.auth.input.LoginInput;
import com.example.app.dto.auth.input.SignUpInput;

import com.example.app.dto.auth.output.LoginOutput;
import com.example.app.dto.auth.output.SignUpOutput;
import com.example.app.entity.UserEntity;
import com.example.app.exception.BusinessException;
import com.example.app.repository.UserRepository;
import com.example.app.service.AuthService;
import com.example.app.util.AuthUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class AuthServiceImpl implements AuthService {


    private ModelMapper modelMapper;


    private ObjectMapper objectMapper;


    private UserRepository userRepository;

    public AuthServiceImpl(ModelMapper modelMapper, ObjectMapper objectMapper, UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private PasswordEncoder passwordEncoder;

    @Value("${app.secret-key}")
    private String secretKey;


    @Override
    public SignUpOutput signUp(SignUpInput input) {
        String userName = input.getUsername();
        UserEntity existed = userRepository.findByUsername(userName);
        if (existed != null) {
            throw new BusinessException("EXISTED_USER", "Tai khoan da ton tai");
        }

        String cardIdNumber = input.getCardIdNumber();
        UserEntity existedCardIdNumber = userRepository.findFistByCardIdNumber(cardIdNumber);
        if (existedCardIdNumber != null) {
            throw new BusinessException("EXISTED_CARD_ID_NUMBER", "Giay to tuy than da ton tai");
        }

        String phoneNumber = input.getPhoneNumber();
        UserEntity existedPhoneNumber = userRepository.findFistByPhoneNumber(phoneNumber);
        if (existedPhoneNumber != null) {
            throw new BusinessException("EXISTED_PHONE_NUMBER", "So dien thoai da ton tai");
        }

        UserEntity newUser = modelMapper.map(input, UserEntity.class);
        newUser.setRole(RoleEnum.USER);
        newUser.setPassword(passwordEncoder.encode(input.getPassword()));

        newUser.setType(UserTypeEnum.BASIC);
        userRepository.save(newUser);

        SignUpOutput output = new SignUpOutput();
        output.setUserId(newUser.getId());
        return output;
    }

    @Override
    public LoginOutput login(LoginInput input) {
        String username = input.getUsername();
        UserEntity existedUser = userRepository.findByUsername(username);
        if (existedUser == null) {
            throw new BusinessException("NOT_FOUND_USERNAME", "Tai khoan chua ton tai");
        }
        if (!passwordEncoder.matches(input.getPassword(), existedUser.getPassword())) {
            throw new BusinessException("INCORRECT_PASSWORD", "Tai khoan chua ton tai");
        }

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setUserId(existedUser.getId());
        userAuthentication.setRole(existedUser.getRole());
        userAuthentication.setType(existedUser.getType());
        Map<String, Object> payload = objectMapper.convertValue(userAuthentication, new TypeReference<>() {
        });
        LoginOutput output = new LoginOutput();
        output.setUserId(existedUser.getId());
        output.setToken(AuthUtil.generateToken(payload, secretKey));
        return output;
    }
}
