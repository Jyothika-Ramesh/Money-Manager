package com.org.familyexpenseapp.repository;

import com.org.familyexpenseapp.model.Income;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends MongoRepository<Income, String> {

    // Find income for a specific family and month
    Optional<Income> findByFamilynameAndMonth(String familyname, String month);
    // Find all income records for a family
    List<Income> findByFamilyname(String familyname);
}


