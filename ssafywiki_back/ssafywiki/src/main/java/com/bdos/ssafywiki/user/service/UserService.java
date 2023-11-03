package com.bdos.ssafywiki.user.service;

import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.dto.UserDto.Registration;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserDto.Registration checkUserInfo(String name) {
        Optional<User> optionalUser = userRepository.findByEmail(name);
        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();
        return UserDto.Registration.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole().name())
                .campus(user.getCampus())
                .number(user.getNumber())
                .build();
    }

    public String editUser(User user, Registration request) {
        user.setPassword(request.getPassword());
        user.setNickname(request.getNickname());
        if(userRepository.findByEmail(user.getEmail()).isEmpty()){
            return "변경 실패";
        }
        if(userRepository.findByEmail(user.getEmail()).get().getPassword().equals(request.getPassword())){
            return "변경 완료";
        }
        return "변경 실패";
    }
}
