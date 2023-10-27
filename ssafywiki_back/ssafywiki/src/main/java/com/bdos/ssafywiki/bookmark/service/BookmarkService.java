package com.bdos.ssafywiki.bookmark.service;

import com.bdos.ssafywiki.bookmark.dto.BookmarkDto;
import com.bdos.ssafywiki.bookmark.entity.Bookmark;
import com.bdos.ssafywiki.bookmark.mapper.BookmarkMapper;
import com.bdos.ssafywiki.bookmark.repository.BookmarkRepository;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.document.repository.DocumentRepository;
import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final BookmarkMapper bookmarkMapper;

    public void registBookmark(Long docsId) {
        //사용자, 문서 -> 북마크
        User user = userRepository.findById(1L).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Document document = documentRepository.findById(docsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND));

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setDocument(document);
        bookmarkRepository.save(bookmark);
    }

    public List<BookmarkDto.Detail> getBookmark() {
        User user = userRepository.findById(1L).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        //사용자의 북마크 불러오기
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByUserId(user.getId());

        return bookmarkMapper.toDetailList(bookmarkList);
    }

    public void deleteBookmark(Long docsId) {
        Bookmark bookmark = bookmarkRepository.findByDocsId(docsId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND));

        bookmarkRepository.delete(bookmark);
    }
}
