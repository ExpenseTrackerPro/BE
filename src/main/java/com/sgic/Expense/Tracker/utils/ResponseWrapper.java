package com.sgic.Expense.Tracker.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {
    private int statusCode;
    private String statusMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
