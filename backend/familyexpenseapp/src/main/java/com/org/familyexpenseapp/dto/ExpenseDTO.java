package com.org.familyexpenseapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {
    private String category;
    private double amountSpent;
    private String familyname;
    private LocalDate date;
    private String note;
}

