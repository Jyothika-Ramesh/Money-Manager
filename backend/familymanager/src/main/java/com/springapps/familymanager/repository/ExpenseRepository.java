package com.springapps.familymanager.repository;

import com.springapps.familymanager.entity.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {
    Expense findByCategory(String category);
}

