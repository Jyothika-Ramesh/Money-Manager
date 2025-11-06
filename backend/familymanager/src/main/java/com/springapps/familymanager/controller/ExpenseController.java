package com.springapps.familymanager.controller;

import com.springapps.familymanager.dto.ExpenseDTO;
import com.springapps.familymanager.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService service;

    @GetMapping("/user")
    public List<ExpenseDTO> getAll() {
        return service.getAllExpenses();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO response = service.addExpense(expenseDTO);
        if(response != null){
        return ResponseEntity.status(HttpStatus.OK).body("added successfully");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("already exists update expenses");
    }

    @PatchMapping("/{id}")  // Unlike PUT, which typically requires sending the entire resource for an update, PATCH allows clients to send only the fields or parts of a resource that need to be changed
    @PreAuthorize("hasRole('ADMIN')")
    public ExpenseDTO update(@PathVariable String id, @RequestBody ExpenseDTO expenseDTO) {
        expenseDTO.setId(id);
        return service.updateExpense(id,expenseDTO);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id) {
        service.deleteExpense(id);
    }
}
