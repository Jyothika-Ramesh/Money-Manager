package com.springapps.familymanager.dto;

import com.springapps.familymanager.entity.Expense;
import lombok.Data;

@Data
public class ExpenseResponse {
    private String id;
    private String category;
    private double amount;
    private String notes;
    private String date;

    public ExpenseResponse(Expense expense) {
        this.id = expense.getId();
        this.category = expense.getCategory();
        this.amount = expense.getAmount();
        this.notes = expense.getNotes();
        this.date = expense.getDate();
    }

}

