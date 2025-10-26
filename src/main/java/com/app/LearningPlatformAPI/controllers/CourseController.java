package com.app.LearningPlatformAPI.controllers;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Tag(name = "Course API", description = "Endpoints for retrieving course and module details")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Get course details", description = "Retrieve a specific course and its related modules by course ID")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<ResponseDto> getCourse(@PathVariable("courseId") Long id) {
        return new ResponseEntity<>(courseService.getCourse(id), HttpStatus.OK);
    }

    @Operation(summary = "Get module details", description = "Retrieve details of a specific module by its ID")
    @GetMapping("/course/module/{moduleId}")
    public ResponseEntity<ResponseDto> getModule(@PathVariable("moduleId") Long id) {
        return new ResponseEntity<>(courseService.getModule(id), HttpStatus.OK);
    }
}
