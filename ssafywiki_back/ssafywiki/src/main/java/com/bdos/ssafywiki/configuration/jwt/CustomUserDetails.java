package com.bdos.ssafywiki.configuration.jwt;

import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomUserDetails extends User implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime blockedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    // 계정이 만료되지 않았는지
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않은지
    @Override
    public boolean isAccountNonLocked() {
//        if(blockedAt == null) return true;
//
//        LocalDateTime current = LocalDateTime.now();
//        return blockedAt.isBefore(current);
        return true;
    }

    // 계정의 패스워드가 만료되지 않았는지
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용가능한 계정인지
    @Override
    public boolean isEnabled() {
        return true;
    }

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.blockedAt = user.getBlockedAt();
    }
}
