package com.bdos.ssafywiki.user.entity;

import com.bdos.ssafywiki.user.enums.Role;

public class GuestUser extends User{

    public GuestUser () {
        super(null,null,null,null, Role.ROLE_GUEST,null,null,null);
    }
}
