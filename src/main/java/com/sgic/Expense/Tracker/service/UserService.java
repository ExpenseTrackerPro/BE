package com.sgic.Expense.Tracker.service;

import com.sgic.Expense.Tracker.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDto> getAllUsers();
}