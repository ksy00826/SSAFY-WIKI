package com.bdos.ssafywiki.user.service;

import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private UserRepository userRepository;

    // 회원가입 로직
    public User registerUser(UserDto.Registration registrationDto) {
        User user = User.builder()
                .name(registrationDto.getName())
                .password(registrationDto.getPassword())
                .email(registrationDto.getEmail())
                .campus(registrationDto.getCampus())
                .role(registrationDto.getRoll())
                .build();
        return userRepository.save(user);
    }


    public UserDto.Token loginUser(UserDto.Login loginDto) {
        return userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
    }
}
