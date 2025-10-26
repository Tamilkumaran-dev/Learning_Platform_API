package com.app.LearningPlatformAPI.controllers;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/check")
@Tag(name = "Auth Status API", description = "Check if the user is currently logged in")
public class IsLogInController {

    private final AuthService authService;

    @Operation(
            summary = "Check login status",
            description = "Verifies if the current user session is authenticated based on the provided JWT cookie or token."
    )
    @GetMapping("/isLoggedIn")
    public ResponseEntity<ResponseDto> isLoggedin(HttpServletRequest request) {
        return new ResponseEntity<>(authService.isLoggedIn(request), HttpStatus.OK);
    }
}
