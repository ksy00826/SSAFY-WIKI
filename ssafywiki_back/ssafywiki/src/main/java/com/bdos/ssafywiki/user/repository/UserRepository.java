package com.bdos.ssafywiki.user.repository;

import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<String> findAllRefreshTokenByUser(Long id);

    Optional<User> findByEmailAndPassword(String email, String password);
}
