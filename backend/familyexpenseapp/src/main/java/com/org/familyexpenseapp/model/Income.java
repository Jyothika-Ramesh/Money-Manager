package com.org.familyexpenseapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.YearMonth;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "income")
public class Income {
    @Id
    private String id;
    private String familyname;
    private String month;
    private double amount;
}

