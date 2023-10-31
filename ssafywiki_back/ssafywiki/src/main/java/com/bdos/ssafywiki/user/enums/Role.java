package com.bdos.ssafywiki.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bdos.ssafywiki.user.enums.Privilege.*;

@RequiredArgsConstructor
public enum Role {

    ADMIN("관리자", Set.of(READ_PRIVILEGE,WRITE_PRIVILEGE,UPDATE_PRIVILEGE,DELETE_PRIVILEGE)),
    PRO("프로", Set.of(READ_PRIVILEGE,WRITE_PRIVILEGE,UPDATE_PRIVILEGE,DELETE_PRIVILEGE)),
    CONSULTANT("컨설턴트", Set.of(READ_PRIVILEGE,WRITE_PRIVILEGE,UPDATE_PRIVILEGE,DELETE_PRIVILEGE)),
    COACH("코치", Set.of(READ_PRIVILEGE,WRITE_PRIVILEGE,UPDATE_PRIVILEGE,DELETE_PRIVILEGE)),
    USER("9기,10기", Set.of(READ_PRIVILEGE,WRITE_PRIVILEGE,UPDATE_PRIVILEGE,DELETE_PRIVILEGE)),
    GUEST("게스트", Set.of(READ_PRIVILEGE));

    @Getter
    private final String key;
    @Getter
    private final Set<Privilege> privileges;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = getPrivileges()
                .stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
