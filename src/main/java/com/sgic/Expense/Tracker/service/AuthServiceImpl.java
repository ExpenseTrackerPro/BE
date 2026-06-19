package com.sgic.Expense.Tracker.service;

import com.sgic.Expense.Tracker.dto.RegisterRequestDto;
import com.sgic.Expense.Tracker.dto.UserDto;
import com.sgic.Expense.Tracker.entity.User;
import com.sgic.Expense.Tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(RegisterRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already in use: " + request.getEmail()
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}