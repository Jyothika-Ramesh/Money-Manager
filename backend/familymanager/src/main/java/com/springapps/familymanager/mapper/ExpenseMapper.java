package com.springapps.familymanager.mapper;

import com.springapps.familymanager.dto.ExpenseDTO;
import com.springapps.familymanager.entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseDTO toDTO(Expense expense) {
        if (expense == null) return null;

        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setCategory(expense.getCategory());
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getDate());
        dto.setNotes(expense.getNotes());
        return dto;
    }

    public Expense toEntity(ExpenseDTO dto) {
        if (dto == null) return null;

        Expense expense = new Expense();
        expense.setId(dto.getId());
        expense.setCategory(dto.getCategory());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setNotes(dto.getNotes());
        return expense;
    }
}
