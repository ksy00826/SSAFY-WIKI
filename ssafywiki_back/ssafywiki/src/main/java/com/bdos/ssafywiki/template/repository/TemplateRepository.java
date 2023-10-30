package com.bdos.ssafywiki.template.repository;

import com.bdos.ssafywiki.template.entity.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    @Query("select t from Template t where t.user.id = :userId and t.title like concat('%', :keyword, '%')")
    Page<Template> findAllWithAuthorAndKeyword(String keyword, Long userId, Pageable pageable);

    @Query("select t from Template t where t.user.id != :userId and t.title like concat('%', :keyword, '%')")
    Page<Template> findAllWithNotAuthorAndKeyword(String keyword, Long userId, Pageable pageable);

    @Query("select t from Template t where t.user.id = :userId")
    Page<Template> findAllWithAuthor(Long userId, Pageable pageable);
}
