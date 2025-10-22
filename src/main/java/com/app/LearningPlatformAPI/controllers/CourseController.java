package com.app.LearningPlatformAPI.controllers;


import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CourseController {

    private CourseService courseService;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ResponseDto> getCourse(@PathVariable("courseId") Long id){
        return new ResponseEntity<>(courseService.getCourse(id), HttpStatus.OK);
    }

    @GetMapping("/course/module/{moduleId}")
    public ResponseEntity<ResponseDto> getModule(@PathVariable("moduleId") Long id){
        return new ResponseEntity<>(courseService.getModule(id),HttpStatus.OK);
    }
}
