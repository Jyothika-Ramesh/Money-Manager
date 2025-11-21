package com.org.familyexpenseapp.repository;

import com.org.familyexpenseapp.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByCategory(String category);
    boolean existsByCategory(String category);
}

