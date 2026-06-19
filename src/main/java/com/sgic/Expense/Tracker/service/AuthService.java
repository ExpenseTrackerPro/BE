package com.sgic.Expense.Tracker.service;

import com.sgic.Expense.Tracker.dto.RegisterRequestDto;
import com.sgic.Expense.Tracker.dto.UserDto;

public interface AuthService {

    UserDto register(RegisterRequestDto request);
}