package com.bdos.ssafywiki.user.service;

import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;


    public Optional<User> searchUser(String name) {
        return userRepository.findByEmail(name);
    }
}
