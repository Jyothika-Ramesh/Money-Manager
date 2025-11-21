package com.org.familyexpenseapp.service;

import com.org.familyexpenseapp.dto.ExpenseDTO;
import com.org.familyexpenseapp.model.Expense;
import com.org.familyexpenseapp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepo;

    // Member: Add expense for their own family
    public ResponseEntity<?> addExpense(ExpenseDTO dto) {
        Expense expense = new Expense();
        expense.setFamilyname(dto.getFamilyname());
        expense.setCategory(dto.getCategory());
        expense.setAmountSpent(dto.getAmountSpent());
        expense.setDate(dto.getDate());
        expense.setNote(dto.getNote());

        expenseRepo.save(expense);
        return ResponseEntity.ok("Expense saved");
    }

    public ResponseEntity<?> updateExpense(ExpenseDTO dto, String id) {
        Optional<Expense> expOpt = expenseRepo.findById(id);
        if (expOpt.isPresent()) {
            Expense expense = expOpt.get(); //use existing object
            expense.setCategory(dto.getCategory());
            expense.setAmountSpent(dto.getAmountSpent());
            expense.setDate(dto.getDate());
            expense.setNote(dto.getNote());

            // Optional: enforce familyname match for scoped update
            if (!expense.getFamilyname().equals(dto.getFamilyname())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied for this family");
            }

            expenseRepo.save(expense);
            return ResponseEntity.ok("Expense updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Member: View expenses for their family and month
    public ResponseEntity<?> getExpensesForFamily(String familyname, String month) {
        List<Expense> expenses = expenseRepo.findByFamilynameAndMonth(familyname, month);
        return ResponseEntity.ok(expenses);
    }

    // Admin: View all expenses for their family
    public ResponseEntity<?> getAllExpensesForFamily(String familyname) {
        List<Expense> expenses = expenseRepo.findByFamilyname(familyname);
        return ResponseEntity.ok(expenses);
    }

    // Admin: Delete expense only if it belongs to their family
    public ResponseEntity<?> deleteExpenseScoped(String id, String familyname) {
        Optional<Expense> expense = expenseRepo.findById(id);
        if (expense.isPresent() && expense.get().getFamilyname().equals(familyname)) {
            expenseRepo.deleteById(id);
            return ResponseEntity.ok("Expense deleted");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }
}

