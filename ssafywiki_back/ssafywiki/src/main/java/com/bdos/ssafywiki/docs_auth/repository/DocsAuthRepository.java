package com.bdos.ssafywiki.docs_auth.repository;

import com.bdos.ssafywiki.docs_auth.entity.DocsAuth;
import com.bdos.ssafywiki.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocsAuthRepository extends JpaRepository<DocsAuth, Long> {

    Optional<DocsAuth> findByDocument(Document document);
}
