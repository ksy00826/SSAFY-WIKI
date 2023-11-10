package com.bdos.ssafywiki.report.mapper;

import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.report.dto.DocumentReportDto;
import com.bdos.ssafywiki.report.entity.DocumentReport;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    DocumentReportDto.Response toResponse(DocumentReport documentReport);
    List<DocumentReportDto.Response> toResonseList(List<DocumentReport> documentReportList);
    DocumentReportDto.Page toDtoPage(Page<DocumentReport> documentReportPage);
}
