package com.springapps.familymanager.service;

import com.springapps.familymanager.dto.ExpenseDTO;
import com.springapps.familymanager.entity.Expense;
import com.springapps.familymanager.mapper.ExpenseMapper;
import com.springapps.familymanager.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired private ExpenseRepository expenseRepo;
    @Autowired private ExpenseMapper mapper;
    @Autowired private ExpenseEventProducer eventProducer;

    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepo.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ExpenseDTO addExpense(ExpenseDTO request) {
        Expense exists = expenseRepo.findByCategory(request.getCategory());
        if (exists == null) {
            Expense expenseEntity = mapper.toEntity(request); //  Convert DTO to Entity
            Expense saved = expenseRepo.save(expenseEntity);         //  Save Entity
            ExpenseDTO savedDto = mapper.toDTO(saved);
            // publish to kafka topic
            try {
                eventProducer.publishExpenseEvent(savedDto);
            } catch (Exception ignored) {
                // avoid failing the API if kafka is down; event publishing is best-effort
            }
            return savedDto;                       //  Return DTO
        }
        return null;
    }

    @Autowired
    private ExpenseMapper expenseMapper;

    public ExpenseDTO updateExpense(String id, ExpenseDTO request) {
        Expense expense = expenseRepo.findById(id).orElseThrow();

        expense.setCategory(request.getCategory());
        expense.setAmount(request.getAmount());
        expense.setNotes(request.getNotes());
        expense.setDate(request.getDate());

        Expense updated = expenseRepo.save(expense);
        ExpenseDTO updatedDto = expenseMapper.toDTO(updated);
        try {
            eventProducer.publishExpenseEvent(updatedDto);
        } catch (Exception ignored) {
        }
        return updatedDto; //  Convert entity to DTO before returning
    }


    public void deleteExpense(String id) {
        // try to fetch existing entity to publish a delete event with context
        Expense existing = expenseRepo.findById(id).orElse(null);
        if (existing != null) {
            ExpenseDTO dto = expenseMapper.toDTO(existing);
            try {
                eventProducer.publishExpenseEvent(dto);
            } catch (Exception ignored) {
            }
        }
        expenseRepo.deleteById(id);
    }
}

