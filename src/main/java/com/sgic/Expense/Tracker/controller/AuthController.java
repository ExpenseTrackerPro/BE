package com.sgic.Expense.Tracker.controller;

import com.sgic.Expense.Tracker.dto.AuthResponseDto;
import com.sgic.Expense.Tracker.dto.LoginRequestDto;
import com.sgic.Expense.Tracker.dto.RegisterRequestDto;
import com.sgic.Expense.Tracker.dto.UserDto;
import com.sgic.Expense.Tracker.entity.User;
import com.sgic.Expense.Tracker.enums.RestApiResponseStatusCodes;
import com.sgic.Expense.Tracker.repository.UserRepository;
import com.sgic.Expense.Tracker.security.JwtService;
import com.sgic.Expense.Tracker.service.AuthService;
import com.sgic.Expense.Tracker.utils.ResponseWrapper;
import com.sgic.Expense.Tracker.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController exposes public registration and login endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private UserDto response;

    /**
     * POST /api/auth/register
     * <p>
     * Registers a new user account into the database so they can
     * authenticate later.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto request) {
        UserDto response = authService.register(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        return ResponseEntity.ok(new
                ResponseWrapper<>(
                        RestApiResponseStatusCodes.ACCEPTED.getCode(),
                        ValidationMessages.SUCCESS,
                        response
        ));
    }

    /**
     * POST /api/auth/login
     * <p>
     * Authenticates a user using credentials supplied in the JSON body.
     * Generates and returns a JWT token for stateless communication.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
        try {
            // 1. Authenticate credentials via AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 2. Extract UserDetails from authentication context
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 3. Generate JWT token
            String token = jwtService.generateToken(userDetails);

            // 4. Retrieve database user details
            User user = userRepository.findByEmail(request.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("User not found after authentication"));

            // 5. Build and return the response containing the token
            AuthResponseDto authResponseDto = AuthResponseDto.builder()
                    .token(token)
                    .email(user.getEmail())
                    .name(user.getName())
                    .build();

            return ResponseEntity.ok(authResponseDto);

        } catch (org.springframework.security.core.AuthenticationException e) {
            // Return a detailed message if credentials or database matching fails
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(java.util.Map.of(
                            "error", "Authentication failed",
                            "message", "Invalid email or password. If you registered this user before adding security, please delete the database record and register a new one to ensure the password is encrypted with BCrypt."
                    ));
        }
    }
}

