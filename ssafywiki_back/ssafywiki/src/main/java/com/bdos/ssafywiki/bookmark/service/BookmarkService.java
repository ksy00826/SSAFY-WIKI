package com.bdos.ssafywiki.bookmark.service;

import com.bdos.ssafywiki.bookmark.dto.BookmarkDto;
import com.bdos.ssafywiki.bookmark.entity.Bookmark;
import com.bdos.ssafywiki.bookmark.mapper.BookmarkMapper;
import com.bdos.ssafywiki.bookmark.repository.BookmarkRepository;
import com.bdos.ssafywiki.configuration.jwt.CustomUserDetails;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.document.repository.DocumentRepository;
import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final BookmarkMapper bookmarkMapper;

    public void registBookmark(Long docsId, User user) {
        //사용자, 문서 -> 북마크
        Document document = documentRepository.findById(docsId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND));

        //이미 해당 문서의 북마크 존재하는지 검사
        Bookmark bookmark = bookmarkRepository.findByDocsId(docsId).orElse(null);
        if(bookmark != null){
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_CONFLICT);
        }

        bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setDocument(document);
        bookmarkRepository.save(bookmark);
    }

    public List<BookmarkDto.Detail> getBookmark(Pageable pageable, User user) {

        //사용자의 북마크 불러오기
        Page<Bookmark> bookmarkList = bookmarkRepository.findAllByUserId(user.getId(), pageable);

        return bookmarkMapper.toDetailList(bookmarkList.getContent());
    }

    public void deleteBookmark(Long docsId) {
        Bookmark bookmark = bookmarkRepository.findByDocsId(docsId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND));

        bookmarkRepository.delete(bookmark);
    }
}
