package com.bdos.ssafywiki.docs_category.repository;

import com.bdos.ssafywiki.discussion.entity.Discussion;
import com.bdos.ssafywiki.docs_category.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    <T> Optional<T> findByName(String category);

}
