package com.bdos.ssafywiki.template.service;

import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.template.dto.TemplateDto;
import com.bdos.ssafywiki.template.entity.Template;
import com.bdos.ssafywiki.template.mapper.TemplateMapper;
import com.bdos.ssafywiki.template.repository.TemplateRepository;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final TemplateMapper templateMapper;

    public TemplateDto.Detail createTemplate(TemplateDto.Post post) {
        User user = userRepository.findById(1L).orElse(null); //임시

        Template template = Template.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .secret(post.isSecret())
                .build();
        template.setUser(user);
        templateRepository.save(template);

        return templateMapper.toDetail(template);
    }

    public List<TemplateDto.Preview> readTemplateList() {
        List<Template> templateList = templateRepository.findAll();

        return templateMapper.toPreviewList(templateList);
    }

    public TemplateDto.Detail readTemplateDetail(Long templateId) {
        Template template = templateRepository.findById(templateId).orElse(null);
        if (template == null) return null;
        return templateMapper.toDetail(template);
    }
}
