package com.bdos.ssafywiki.revision.service;

import com.bdos.ssafywiki.revision.entity.Revision;
import com.bdos.ssafywiki.revision.repository.RevisionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.DiffResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevisionService {
    private final RevisionRepository revisionRepository;

    public Page<Revision> getHistory(long docsId, Pageable pageable) {

        return revisionRepository.findAllByDocumentJoinComment(docsId, pageable);
    }

    public Revision getDetail(long docsId, long revNumber) {

        return revisionRepository.findByDocumentIdAndNumber(docsId, revNumber);
    }
}
