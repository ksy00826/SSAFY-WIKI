package com.bdos.ssafywiki.template.mapper;

import com.bdos.ssafywiki.template.dto.TemplateDto;
import com.bdos.ssafywiki.template.entity.Template;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    TemplateDto.Detail toDetail(Template template);
}
