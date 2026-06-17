package com.sgic.Expense.Tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesDto {

    private Long id;
    private String name;
    private String description;
    private String status;
}
