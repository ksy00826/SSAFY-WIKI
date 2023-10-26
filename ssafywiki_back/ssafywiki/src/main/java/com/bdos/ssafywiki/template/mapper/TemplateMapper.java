package com.bdos.ssafywiki.template.mapper;

import com.bdos.ssafywiki.template.dto.TemplateDto;
import com.bdos.ssafywiki.template.entity.Template;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    TemplateDto.Detail toDetail(Template template);

    @Mapping(source = "template.user.id", target = "author")
    TemplateDto.Preview toPreview(Template template);

    List<TemplateDto.Preview> toPreviewList(List<Template> templateList);
}
