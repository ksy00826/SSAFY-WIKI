package com.bdos.ssafywiki.template.service;

import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.template.dto.TemplateDto;
import com.bdos.ssafywiki.template.entity.Template;
import com.bdos.ssafywiki.template.mapper.TemplateMapper;
import com.bdos.ssafywiki.template.repository.TemplateRepository;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final TemplateMapper templateMapper;

    public TemplateDto.Detail createTemplate(TemplateDto.Post post, User user) {
        Template template = Template.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .secret(post.isSecret())
                .build();
        template.setUser(user);
        templateRepository.save(template);

        return templateMapper.toDetail(template);
    }

    public List<TemplateDto.Preview> readTemplateList(boolean isMyTemplate, Pageable pageable, User user) {
        Page<Template> templateList = null;
        if (isMyTemplate){
            //임시 사용자
            templateList = templateRepository.findAllWithAuthor(user.getId(), pageable);
        }
        else{
            templateList = templateRepository.findAllNotWithAuthor(user.getId(), pageable);
        }
        return templateMapper.toPreviewList(templateList.getContent());
    }

    public TemplateDto.Detail readTemplateDetail(Long templateId, User user) {
        Template template = templateRepository.findById(templateId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.TEMPLATE_NOT_FOUND));
        return templateMapper.toDetail(template);
    }

    public void deleteTemplate(Long templateId) {
        Template template = templateRepository.findById(templateId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.TEMPLATE_NOT_FOUND));
        templateRepository.delete(template);
    }

    public List<TemplateDto.Preview> searchTemplate(String keyword, boolean isMyTemplate, Pageable pageable, User user) {
        Page<Template> templateList = null;
        if (isMyTemplate){
            //임시 : JWT
            templateList = templateRepository.findAllWithAuthorAndKeyword(keyword, user.getId(), pageable);
        }
        else{
            templateList = templateRepository.findAllWithNotAuthorAndKeyword(keyword, user.getId(), pageable);
        }
        return templateMapper.toPreviewList(templateList.getContent());
    }

    @Transactional
    public void modifyAuthority(Long templateId, Boolean newSecret) {
        Template template = templateRepository.findById(templateId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.TEMPLATE_NOT_FOUND));
        template.setSecret(newSecret);
//        templateRepository.save(template);
    }
}
