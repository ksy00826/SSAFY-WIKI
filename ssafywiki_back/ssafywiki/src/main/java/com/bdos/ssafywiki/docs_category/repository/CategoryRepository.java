package com.bdos.ssafywiki.docs_category.repository;

import com.bdos.ssafywiki.docs_category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    <T> Optional<T> findByName(String category);
}
