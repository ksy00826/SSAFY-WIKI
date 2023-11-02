package com.bdos.ssafywiki.configuration.jwt;

import com.bdos.ssafywiki.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@NoArgsConstructor
@ToString
public class CustomUserDetails extends User implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    // 계정이 만료되지 않았는지
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않은지
    @Override
    public boolean isAccountNonLocked() {
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
        this.user = user;
    }
}
