package com.bdos.ssafywiki.document.service;

import com.bdos.ssafywiki.document.dto.DocumentReadDto;
import com.bdos.ssafywiki.document.dto.DocumentWriteDto;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.document.repository.DocumentRepository;
import com.bdos.ssafywiki.revision.entity.Comment;
import com.bdos.ssafywiki.revision.entity.Content;
import com.bdos.ssafywiki.revision.entity.Revision;
import com.bdos.ssafywiki.revision.repository.CommentRepository;
import com.bdos.ssafywiki.revision.repository.ContentRepository;
import com.bdos.ssafywiki.revision.repository.RevisionRepository;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.ReverbType;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final CommentRepository commentRepository;
    private final ContentRepository contentRepository;
    private final RevisionRepository revisionRepository;
    private final UserRepository userRepository;

    public DocumentReadDto writeDocs(DocumentWriteDto documentWriteDto) {
        //로그인 한 사용자(작성 유저) : JWT
        User user = new User("qqq@naver.com", "pwpw", "ksy", "sysy", "ssafy", "010", "buk", "token");
        //일단 유저를 다른 곳에 연관관계로 등록하기 위해 임시로 저장
        userRepository.save(user);

        //1. Document entity 생성
        Document document = new Document(documentWriteDto.getTitle()); //redirect, deleted는 기본값이 false

        //1.1 연관관계 등록
        document.setUser(user);
        document.setParent(null);
        document.setChildren(new ArrayList<>());
        documentRepository.save(document);

        //2. Revision entity 생성
        Long no = 1L; //임시
        Revision revision = new Revision(0L, no);

        //2.1 Content entity 생성 + Comment entity 생성
        Content content = new Content(documentWriteDto.getContent());
        Comment comment = new Comment("First Document");
        contentRepository.save(content);
        commentRepository.save(comment);

        //2.1 연관관계 등록
        revision.setContent(content);
        revision.setComment(comment);
        revision.setDocument(document);
        revision.setUser(user);

        revisionRepository.save(revision);

        //만들어진 문서 엔티티 리턴
        DocumentReadDto documentReadDto = new DocumentReadDto(document.getId(), user.getId(), document.getTitle(), content.getText());
        return documentReadDto;
    }
}
