package com.bdos.ssafywiki.user.mapper;

import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto.Version toVersion(User user);

    UserDto.Info toInfo(User user);
}
