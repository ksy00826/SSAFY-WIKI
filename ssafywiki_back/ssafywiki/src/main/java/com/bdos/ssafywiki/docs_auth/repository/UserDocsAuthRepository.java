package com.bdos.ssafywiki.docs_auth.repository;

import com.bdos.ssafywiki.docs_auth.entity.DocsAuth;
import com.bdos.ssafywiki.docs_auth.entity.UserDocsAuth;
import com.bdos.ssafywiki.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserDocsAuthRepository extends JpaRepository<UserDocsAuth, Long> {

    List<UserDocsAuth> findAllByDocsAuth(DocsAuth auth);

    Optional<UserDocsAuth> findByDocsAuthAndUser(DocsAuth findAuth, User findUser);

    @Query(value = "SELECT u FROM UserDocsAuth u WHERE u.docsAuth.id = :authId AND u.user.id = :userId")
    Optional<UserDocsAuth> findByDocsAuthIdAndUserId(Long authId, Long userId);

    Set<UserDocsAuth> findAllByUserId(Long id);
}
