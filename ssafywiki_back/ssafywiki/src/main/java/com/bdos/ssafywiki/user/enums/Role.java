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

    ADMIN("관리자", Set.of(READ_LV1,WRITE_LV1,UPDATE_LV1,READ_LV2,WRITE_LV2, UPDATE_LV2, DELETE)),
    PRO("프로", Set.of(READ_LV1,WRITE_LV1,UPDATE_LV1,READ_LV2,WRITE_LV2, UPDATE_LV2, DELETE)),
    CONSULTANT("컨설턴트", Set.of(READ_LV1,WRITE_LV1,UPDATE_LV1, READ_LV2,WRITE_LV2, UPDATE_LV2, DELETE)),
    COACH("코치", Set.of(READ_LV1,WRITE_LV1, UPDATE_LV1, READ_LV2,WRITE_LV2, UPDATE_LV2)),
    USER9("9기", Set.of(READ_LV1,WRITE_LV1, UPDATE_LV1)),
    USER10("10기", Set.of(READ_LV1,WRITE_LV1, UPDATE_LV1)),
    GUEST("게스트", Set.of(READ_LV1));

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

    public static Role krToRole(String str) {
        for (Role status : Role.values()) {
            if (status.getKey().equals(str)) {
                return status;
            }
        }
        return null;
    }

}
