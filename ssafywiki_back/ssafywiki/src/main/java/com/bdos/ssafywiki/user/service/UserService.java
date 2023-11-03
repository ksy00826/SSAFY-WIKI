package com.bdos.ssafywiki.user.service;

import com.bdos.ssafywiki.user.dto.UserDto.Registration;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import java.util.Dictionary;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public Optional<User> searchUser(String name) {
        return userRepository.findByEmail(name);
    }

    public String editUser(User user, Registration request) {
        user.setPassword(request.getPassword());
        user.setNickname(request.getNickname());
        if(userRepository.findByEmail(user.getEmail()).get().getPassword().equals(request.getPassword())){
            return "변경 완료";
        }
        return "변경 실패";
    }
}
