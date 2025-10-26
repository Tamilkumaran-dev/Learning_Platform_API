package com.app.LearningPlatformAPI.controllers;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Tag(
        name = "User Management API",
        description = "Endpoints for user profile, enrollment, module completion, and learning progress."
)
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Get user profile",
            description = "Fetches the authenticated user's profile details."
    )
    @GetMapping("/profile")
    public ResponseEntity<ResponseDto> getUserProfile(HttpServletRequest request) {
        return new ResponseEntity<>(userService.getProfile(request), HttpStatus.OK);
    }

    @Operation(
            summary = "Enroll in a course",
            description = "Enrolls the logged-in user into a specific course using its ID."
    )
    @PostMapping("/enrollCourse/{courseId}")
    public ResponseEntity<ResponseDto> enrolledCourse(
            @PathVariable("courseId") Long Id,
            HttpServletRequest request) {
        return new ResponseEntity<>(userService.enrollCourse(request, Id), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Complete a module",
            description = "Marks a specific module within a course as completed by the user."
    )
    @PostMapping("/completeModule/{courseId}/{moduleId}")
    public ResponseEntity<ResponseDto> completeModule(
            @PathVariable("moduleId") Long id,
            @PathVariable("courseId") Long courseId,
            HttpServletRequest request) {
        return new ResponseEntity<>(userService.completeModuleService(request, courseId, id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get user learning progress",
            description = "Retrieves the list of enrolled courses and progress for the logged-in user."
    )
    @GetMapping("/getMyLearning")
    public ResponseEntity<ResponseDto> myLearning(HttpServletRequest request) {
        return new ResponseEntity<>(userService.myLearningService(request), HttpStatus.OK);
    }
}
