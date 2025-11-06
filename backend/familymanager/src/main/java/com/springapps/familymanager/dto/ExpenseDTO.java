package com.springapps.familymanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {
    @Id
    private String id;
    private String category;
    private double amount;
    private String notes;
    private String date;

}

