package tech.mayanktiwari.auth.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import tech.mayanktiwari.auth.dto.request.RegisterRequest;
import tech.mayanktiwari.auth.dto.response.UserResponseDto;
import tech.mayanktiwari.auth.service.AuthService;
import tech.mayanktiwari.common.api.ApiResponse;
import tech.mayanktiwari.common.builder.ResponseBuilder;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(
            @RequestBody
            @Valid
            RegisterRequest registerRequest
    ) {
        UserResponseDto response = authService.registerUser(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(ResponseBuilder.success(response, "User registered successfully",
                                                           HttpStatus.CREATED));

    }
}
