package com.org.familyexpenseapp.controller;

import com.org.familyexpenseapp.dto.ExpenseDTO;
import com.org.familyexpenseapp.model.User;
import com.org.familyexpenseapp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:3000")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // Member: Add expense for their own family
    @PostMapping
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        dto.setFamilyname(user.getFamilyname());
        return expenseService.addExpense(dto);
    }

    //ADMIN: update expense
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateExpense(@PathVariable String id, @RequestBody ExpenseDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        dto.setFamilyname(user.getFamilyname());
        return expenseService.updateExpense(dto,id);
    }

    //  Member: View expenses for current month
    @GetMapping("/family")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getExpensesForFamily(@RequestParam String month, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return expenseService.getExpensesForFamily(user.getFamilyname(), month);
    }

    //  Admin: View all expenses for their own family
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllExpensesForFamily(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return expenseService.getAllExpensesForFamily(user.getFamilyname());
    }

    // Admin: Delete expense by ID (scoped)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteExpense(@PathVariable String id, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return expenseService.deleteExpenseScoped(id, user.getFamilyname());
    }
}



