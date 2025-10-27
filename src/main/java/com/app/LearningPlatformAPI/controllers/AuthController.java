package com.app.LearningPlatformAPI.controllers;

import com.app.LearningPlatformAPI.dto.LoginDto;
import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.AdminDb;
import com.app.LearningPlatformAPI.entities.UserDb;
import com.app.LearningPlatformAPI.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication API", description = "Handles user and admin sign-up, sign-in, and logout")
public class AuthController {

    private AuthService authService;

    @Operation(summary = "Register a new user")
    @PostMapping("/signUp")
    public ResponseEntity<ResponseDto> signUpUser(@RequestBody UserDb userDb) {
        return new ResponseEntity<>(authService.SignUp(userDb), HttpStatus.CREATED);
    }

    @Operation(summary = "Login user and set JWT cookie")
    @PostMapping("/signIn")
    public ResponseEntity<ResponseDto> signInUser(@RequestBody LoginDto login, HttpServletResponse response) {

        ResponseDto res = authService.SignIn(login);

        if (res.getData().isPresent()) {
            ResponseCookie cookie = ResponseCookie.from("jwt", (String) res.getData().get())
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(Duration.ofDays(100)) // 12 hours
                    .sameSite("None")
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Register a new admin user")
    @PostMapping("/adminSignUp")
    public ResponseEntity<ResponseDto> signUpAdmin(@RequestBody AdminDb adminDb) {
        return new ResponseEntity<>(authService.RegisterAdmin(adminDb), HttpStatus.CREATED);
    }

    @Operation(summary = "Logout current user (clear JWT cookie)")
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return new ResponseEntity<>(new ResponseDto("Logout successfully", "Logout"), HttpStatus.OK);
    }
}
