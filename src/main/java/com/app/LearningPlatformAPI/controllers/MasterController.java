package com.app.LearningPlatformAPI.controllers;

import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.AdminDb;
import com.app.LearningPlatformAPI.entities.MasterDb;
import com.app.LearningPlatformAPI.services.MasterUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(
        name = "Master Management API",
        description = "Endpoints for managing master users, admins, and related operations."
)
public class MasterController {

    private final MasterUserService masterService;

    @Operation(
            summary = "Register a master user",
            description = "Creates a new master account with elevated privileges."
    )
    @PostMapping("/auth/master/signUpp")
    public ResponseEntity<ResponseDto> addMasterUser(@RequestBody MasterDb master) {
        return new ResponseEntity<>(masterService.signUpMaster(master), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get master profile",
            description = "Fetches the profile information of the currently authenticated master user."
    )
    @GetMapping("/master/profile")
    public ResponseEntity<ResponseDto> getMasterProfile(HttpServletRequest request) {
        return new ResponseEntity<>(masterService.getMasterProfile(request), HttpStatus.OK);
    }

    @Operation(
            summary = "Find admin by ID",
            description = "Retrieves a specific admin’s details using their unique ID."
    )
    @GetMapping("master/findAdmin/{id}")
    public ResponseEntity<ResponseDto> findAdmin(@PathVariable("id") Long id) {
        return new ResponseEntity<>(masterService.getAdmin(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get unverified admins",
            description = "Searches and paginates through unverified admin accounts based on a keyword."
    )
    @GetMapping("/master/getAdminUser/{keyword}/{page}/{size}")
    public ResponseEntity<Page<AdminDb>> getUnVerified(@PathVariable("keyword") String keyword,@PathVariable("page") int page, @PathVariable("size") int size) {
        return new ResponseEntity<>(masterService.getUnVerifiedAdmin(keyword, page, size), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete admin by ID",
            description = "Removes an admin account and associated data from the system."
    )
    @DeleteMapping("/master/deleteAdmin/{adminId}")
    public ResponseEntity<ResponseDto> deleteAdmin(@PathVariable("adminId") Long id) {
        return new ResponseEntity<>(masterService.deleteAdmin(id), HttpStatus.OK);
    }
}
