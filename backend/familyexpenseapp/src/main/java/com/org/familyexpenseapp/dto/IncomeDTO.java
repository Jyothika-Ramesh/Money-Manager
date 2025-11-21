package com.org.familyexpenseapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDTO {
    private String familyname;
    private String month; // "2025-11"
    private double amount;
}


