package com.bdos.ssafywiki.document.service;

import com.bdos.ssafywiki.document.dto.DocumentReadDto;
import com.bdos.ssafywiki.document.dto.DocumentWriteDto;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.document.repository.DocumentRepository;
import com.bdos.ssafywiki.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public Document writeDocs(DocumentWriteDto documentWriteDto) {
        //로그인 한 사용자(작성 유저) : JWT
        User user = new User();

        //document entity 생성
        Document document = new Document(documentWriteDto.getTitle()); //redirect, deleted는 기본값이 false

        //연관관계 등록
        //1. 작성 유저
//        document.setUser(user);

        //2. 부모 문서, 하위 문서 초기화
        document.setParent(null);
        document.setChildren(new ArrayList<>());

        //저장
        documentRepository.save(document);

        //만들어진 문서 엔티티 리턴
        return document;
    }
}
