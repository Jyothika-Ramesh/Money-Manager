package com.org.familyexpenseapp.service;

import com.org.familyexpenseapp.dto.IncomeDTO;
import com.org.familyexpenseapp.model.Income;
import com.org.familyexpenseapp.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepo;

    // Add or update income for a given family and month
    public ResponseEntity<?> addOrUpdateIncome(IncomeDTO dto) {
        Optional<Income> existing = incomeRepo.findByFamilynameAndMonth(dto.getFamilyname(), dto.getMonth());

        Income income = existing.orElse(new Income());
        income.setFamilyname(dto.getFamilyname());
        income.setMonth(dto.getMonth());
        income.setAmount(dto.getAmount());

        incomeRepo.save(income);
        return ResponseEntity.ok("Income saved successfully");
    }

    // Member: View income for their family and month
    public ResponseEntity<?> getIncomeForFamily(String familyname, String month) {
        Optional<Income> income = incomeRepo.findByFamilynameAndMonth(familyname, month);
        if (income.isPresent()) {
            return ResponseEntity.ok(income.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No income found");
        }
    }

    // Admin: View all income records for their family
    public ResponseEntity<?> getAllIncomeForFamily(String familyname) {
        List<Income> incomes = incomeRepo.findByFamilyname(familyname);
        return ResponseEntity.ok(incomes);
    }

    // Admin: Delete income only if it belongs to their family
    public ResponseEntity<?> deleteIncomeScoped(String id, String familyname) {
        Optional<Income> income = incomeRepo.findById(id);
        if (income.isPresent() && income.get().getFamilyname().equals(familyname)) {
            incomeRepo.deleteById(id);
            return ResponseEntity.ok("Income deleted");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }
}


