package com.app.LearningPlatformAPI.controllers;


import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.entities.ModulesDb;
import com.app.LearningPlatformAPI.services.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin API", description = "Manage courses, modules, and admin profile")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Get admin profile", description = "Fetch logged-in admin's details")
    @GetMapping("/profile")
    public ResponseEntity<ResponseDto> getProfile(HttpServletRequest request){
        return new ResponseEntity<>(adminService.getAdminProfile(request), HttpStatus.OK);
    }

    @Operation(summary = "Create a new course", description = "Admin creates a new course")
    @PostMapping("/createCourse")
    public ResponseEntity<ResponseDto> createCourse(@RequestBody CourseDb courseDb, HttpServletRequest request){
        return new ResponseEntity<>(adminService.addCourse(courseDb, request), HttpStatus.CREATED);
    }

    @Operation(summary = "Add module to course")
    @PostMapping("/addModule/{courseId}")
    public ResponseEntity<ResponseDto> addModuleToCourse(@PathVariable("courseId") Long id, @RequestBody ModulesDb modulesDb){
        return new ResponseEntity<>(adminService.addModulesIntoTheCourse(id, modulesDb), HttpStatus.CREATED);
    }


    @Operation(summary = "Update the course", description = "Admin can update the course")
    @PostMapping("/updateCourse/{courseId}")
    public ResponseEntity<ResponseDto> updateCourse(@PathVariable("courseId") Long id, @RequestBody CourseDb courseDb){
        return new ResponseEntity<>(adminService.updateCourse(id,courseDb),HttpStatus.OK);
    }

    @Operation(summary = "Update module", description = "Admin can update the module")
    @PostMapping("/updateModule/{moduleId}")
    public ResponseEntity<ResponseDto> updateModule(@PathVariable("moduleId") Long id,@RequestBody ModulesDb modulesDb){
        return new ResponseEntity<>(adminService.UpdateModule(id,modulesDb),HttpStatus.OK);
    }

    @Operation(summary = "Delete a module", description = "Delete a module")
    @DeleteMapping("/deletedModule/{moduleId}")
    public ResponseEntity<ResponseDto> deleteModule(@PathVariable("moduleId") Long id) {
        return new ResponseEntity<>(adminService.dropModule(id), HttpStatus.OK);
    }


    @Operation(summary = "Delete a course")
    @DeleteMapping("/deletedCourse/{courseId}")
    public ResponseEntity<ResponseDto> deleteCourse(@PathVariable("courseId") Long id){
        return new ResponseEntity<>(adminService.dropCourse(id), HttpStatus.OK);
    }


}

