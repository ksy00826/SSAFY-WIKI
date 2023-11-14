package com.bdos.ssafywiki.user.service;

import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.discussion.entity.Discussion;
import com.bdos.ssafywiki.discussion.mapper.DiscussionMapper;
import com.bdos.ssafywiki.discussion.repository.DiscussionRepository;
import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.dto.UserDto.Registration;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DiscussionRepository discussionRepository;
    private final PasswordEncoder passwordEncoder;
    private final DiscussionMapper discussionMapper;
    public UserDto.Registration checkUserInfo(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();
        return UserDto.Registration.builder()
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole().name())
                .campus(user.getCampus())
                .number(user.getNumber())
                .build();
    }

    public HttpStatus editUser(User loginUser, Registration request) {
        User user = userRepository.findById(loginUser.getId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        //비밀번호가 맞을 시 정보 수정 가능
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())){
            user.setNickname(request.getNickname());
            userRepository.save(user);
            return HttpStatus.OK;
        }
        else{
            return HttpStatus.BAD_REQUEST;
        }

//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setNickname(request.getNickname());
//        userRepository.save(user);
//        if(userRepository.findByEmail(user.getEmail()).isEmpty()){
//            return "변경 실패";
//        }
//        boolean result = passwordEncoder.matches(request.getPassword(), user.getPassword());
//        if(result){
//            return "변경 완료";
//        }
//        return "변경 안됨";
    }

    public List<DiscussionDto> getChats(User user) {
        List<Discussion> dbMessageList = discussionRepository.findAllByUser(user.getId());
        List<DiscussionDto> messageList = new ArrayList<>();
        for (Discussion discussion : dbMessageList) {
            DiscussionDto discussionDto = discussionMapper.toDto(discussion);
            discussionDto.setDocsId(discussion.getDocument().getId());
            discussionDto.setContent(discussion.getDocument().getTitle());
            messageList.add(discussionDto);
        }
        return messageList;
    }
}
