package com.sgic.Expense.Tracker.controller;

import com.sgic.Expense.Tracker.dto.UserDto;
import com.sgic.Expense.Tracker.entity.User;
import com.sgic.Expense.Tracker.exceptions.ResourceNotFoundException;
import com.sgic.Expense.Tracker.repository.UserRepository;
import com.sgic.Expense.Tracker.service.UserService;
import com.sgic.Expense.Tracker.utils.EndPointUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(EndPointUrl.USER)
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * GET /api/users
     * Returns all registered users (without passwords).
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * GET /api/users/me
     * Returns the profile of the currently authenticated user.
     *
     * @AuthenticationPrincipal injects the Spring Security User object.
     * We then load our database entity using the email.
     */
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        User dbUser = userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(toDto(dbUser));
    }

    /**
     * GET /api/users/{id}
     * Any authenticated user can fetch a user's public profile.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return ResponseEntity.ok(toDto(user));
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}

