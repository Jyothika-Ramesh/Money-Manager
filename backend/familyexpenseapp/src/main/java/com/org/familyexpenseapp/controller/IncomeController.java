package com.org.familyexpenseapp.controller;

import com.org.familyexpenseapp.dto.IncomeDTO;
import com.org.familyexpenseapp.model.User;
import com.org.familyexpenseapp.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/income")
@CrossOrigin(origins = "http://localhost:3000")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    // Admin: Add or update income for their own family
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addOrUpdateIncome(@RequestBody IncomeDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        if (!user.getFamilyname().equals(dto.getFamilyname())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied for this family");
        }
        return incomeService.addOrUpdateIncome(dto);
    }

    // Member: View income for current month
    @GetMapping("/family")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getIncomeForFamily(@RequestParam String month, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return incomeService.getIncomeForFamily(user.getFamilyname(), month);
    }

    //Admin: View all income records for their own family
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllIncomeForFamily(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return incomeService.getAllIncomeForFamily(user.getFamilyname());
    }

    //Admin: Delete income record by ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteIncome(@PathVariable String id, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return incomeService.deleteIncomeScoped(id, user.getFamilyname());
    }
}


