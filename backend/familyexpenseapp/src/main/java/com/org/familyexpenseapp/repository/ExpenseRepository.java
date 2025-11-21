package com.org.familyexpenseapp.repository;

import com.org.familyexpenseapp.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    // Find all expenses for a family
    List<Expense> findByFamilyname(String familyname);

    // Find expenses for a family and month (prefix match)
    @Query("{ 'familyname': ?0, 'date': { $regex: ?1 } }")
    List<Expense> findByFamilynameAndMonth(String familyname, String monthPrefix);
}


