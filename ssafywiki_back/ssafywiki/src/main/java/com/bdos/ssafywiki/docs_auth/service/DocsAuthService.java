package com.bdos.ssafywiki.docs_auth.service;

import com.bdos.ssafywiki.docs_auth.dto.DocsAuthDto;
import com.bdos.ssafywiki.docs_auth.entity.DocsAuth;
import com.bdos.ssafywiki.docs_auth.entity.UserDocsAuth;
import com.bdos.ssafywiki.docs_auth.mapper.DocsAuthMapper;
import com.bdos.ssafywiki.docs_auth.repository.DocsAuthRepository;
import com.bdos.ssafywiki.docs_auth.repository.UserDocsAuthRepository;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.document.repository.DocumentRepository;
import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.revision.entity.Revision;
import com.bdos.ssafywiki.revision.repository.RevisionRepository;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocsAuthService {

    private final DocumentRepository documentRepository;
    private final DocsAuthRepository docsAuthRepository;
    private final UserDocsAuthRepository userDocsAuthRepository;
    private final UserRepository userRepository;
    private final RevisionRepository revisionRepository;

    private final DocsAuthMapper docsAuthMapper;

    public DocsAuthDto.AuthResponse getAuth(Long docsId, User userDetails) {

        // 사용자 확인.

        // 문서 찾기
        Document document = documentRepository.findById(docsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND));

        DocsAuthDto.AuthResponse.AuthResponseBuilder builder = DocsAuthDto.AuthResponse.builder()
                .read(document.getReadAuth())
                .write(document.getWriteAuth());


        // 사용자 권한 찾기
        DocsAuth auth = docsAuthRepository.findByDocument(document).orElse(null);
        if(auth != null) {
            // 사용자 권한이 있다면 유저도 반환
            List<UserDocsAuth> userlist = userDocsAuthRepository.findAllByDocsAuth(auth);
            List<DocsAuthDto.UserAuthResponse> users = userlist.stream()
                    .map(docsAuthMapper::toUserAuth).toList();

            builder.users(users);
        }

        return builder.build();
    }

    public DocsAuthDto.AuthResponse updateAuth(DocsAuthDto.AuthRequest request, User userDetails) {
        // 사용자 확인.

        // 문서 찾기
        Document document = documentRepository.findById(request.getDocsId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND));

        // setting값이 사용자 지정 권한이라면 권한 번호 찾거나 만들기
        if(request.getRead() == 100 || request.getWrite() == 100) {
            // 사용자 권한 찾기
            DocsAuth auth = docsAuthRepository.findByDocument(document)
                    .orElseGet( () -> {
                        DocsAuth docsAuth = DocsAuth.builder().document(document).build();
                        docsAuthRepository.save(docsAuth);
                        return docsAuth;
                    }
            );

            if(request.getRead() == 100) request.setRead(auth.getId());
            if(request.getWrite() == 100) request.setWrite(auth.getId());

        }

        document.setAuth(request.getRead(), request.getWrite());
        documentRepository.save(document);
        return DocsAuthDto.AuthResponse.builder()
                .read(document.getReadAuth())
                .write(document.getWriteAuth()).build();
    }

    public DocsAuthDto.UserAuthResponse inviteMember(DocsAuthDto.MemberInviteRequest request, User userDetails) {

        // 요청한 유저가 존재하는 유저인가
        User findUser = userRepository.findByEmail(request.getEmail()).orElseThrow( () ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)
        );

        DocsAuth findAuth = docsAuthRepository.findById(request.getAuthId()).orElseThrow(()->
                new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND)
        );

        // 중복된 유저인지 확인
        UserDocsAuth u = userDocsAuthRepository.findByDocsAuthAndUser(findAuth, findUser)
                .orElseGet(()-> {
                    // 못찾았으면. 중복되지 않았으면 저장.
                            UserDocsAuth uu = UserDocsAuth.builder()
                                    .docsAuth(findAuth)
                                    .user(findUser).build();
                            userDocsAuthRepository.save(uu);
                            return uu;
                        }
                );

        return docsAuthMapper.toUserAuth(u);
    }

    public Boolean deleteMember(DocsAuthDto.MemberDeleteRequest request, User userDetails) {

        UserDocsAuth find = userDocsAuthRepository.findByDocsAuthIdAndUserId(request.getAuthId(), request.getUserId())
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.REQUEST_NOT_FOUND));

        userDocsAuthRepository.delete(find);

        return true;
    }

    public List<DocsAuthDto.SimpleDocs> myDocs(User userDetails) {

        Set<UserDocsAuth> auths = userDocsAuthRepository.findAllByUserId(userDetails.getId());

        List<DocsAuthDto.SimpleDocs> docs = auths.stream().map((auth)->{
            Document doc = auth.getDocsAuth().getDocument();
//            Revision rev = revisionRepository.findTop1ByDocumentOrderByIdDesc(doc);

            return DocsAuthDto.SimpleDocs.builder()
                    .title(doc.getTitle())
                    .docsId(doc.getId())
                    .lastModifyTime(doc.getModifiedAt())
                    .build();

        }).toList();


        return docs;
    }
}
