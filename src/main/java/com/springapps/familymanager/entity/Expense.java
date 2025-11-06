package com.springapps.familymanager.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "expenses")
public class Expense {

    @Id
    private String id;
    private String category;
    private double amount;
    private String notes;
    private String date;
}
