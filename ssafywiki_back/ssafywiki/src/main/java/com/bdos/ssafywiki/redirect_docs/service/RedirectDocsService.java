package com.bdos.ssafywiki.redirect_docs.service;

import com.bdos.ssafywiki.diff.MyDiffUtils;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.document.repository.DocumentRepository;
import com.bdos.ssafywiki.document.service.DocumentService;
import com.bdos.ssafywiki.redirect_docs.dto.RedirectDocsDto;
import com.bdos.ssafywiki.redirect_docs.entity.RedirectDocs;
import com.bdos.ssafywiki.redirect_docs.repository.RedirectDocsRepository;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.revision.entity.Comment;
import com.bdos.ssafywiki.revision.entity.Content;
import com.bdos.ssafywiki.revision.entity.Revision;
import com.bdos.ssafywiki.revision.mapper.RevisionMapper;
import com.bdos.ssafywiki.revision.repository.CommentRepository;
import com.bdos.ssafywiki.revision.repository.ContentRepository;
import com.bdos.ssafywiki.revision.repository.RevisionRepository;
import com.bdos.ssafywiki.user.entity.User;
import com.github.difflib.DiffUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RedirectDocsService {

    private final RedirectDocsRepository redirectDocsRepository;
    private final DocumentRepository documentRepository;
    private final CommentRepository commentRepository;
    private final ContentRepository contentRepository;
    private final RevisionRepository revisionRepository;

    private final MyDiffUtils myDiffUtils;
    private final RevisionMapper revisionMapper;
    public RevisionDto.DocsResponse writeDocs(RedirectDocsDto.Post post, User user) {

        //리다이렉트 문서 생성
        Document redirectDocs = Document.builder()
                .title(post.getTitle())
                .redirect(true)
                .deleted(false)
                .readAuth(1L)
                .writeAuth(1L)
                .build();
        redirectDocs.setUser(user);
        redirectDocs.setParent(null);
        redirectDocs.setChildren(new ArrayList<>());
        documentRepository.save(redirectDocs);

        //리다이렉트 문서의 내용(버전) 등록
        String contentString = "[#redirect: " + post.getRedirectTitle() + "](/res/list?title="+post.getRedirectTitle()+")"; //검색 API 연결 - 수정 필요
        Revision revision = Revision.builder()
                .number(1L)
                .diffAmount((long) myDiffUtils.diffLength(DiffUtils.diff(myDiffUtils.splitIntoLines(""), myDiffUtils.splitIntoLines(contentString))))
                .build();

        //2.1 Content entity 생성 + Comment entity 생성
        Content content = new Content(contentString);
        Comment comment = new Comment("First Document");
        contentRepository.save(content);
        commentRepository.save(comment);

        //2.2 연관관계 등록
        revision.setContent(content);
        revision.setComment(comment);
        revision.setDocument(redirectDocs);
        revision.setUser(user);

        revisionRepository.save(revision);

        //리다이렉트 검색어 등록
        RedirectDocs redirectKeyword = RedirectDocs.builder()
                .keyword(post.getRedirectTitle())
                .build();
        redirectKeyword.setRedirectDocs(redirectDocs);
        redirectDocsRepository.save(redirectKeyword);

        //만들어진 문서 DTO 리턴
        return revisionMapper.toResponse(revision);
    }
}
