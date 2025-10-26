package com.app.LearningPlatformAPI.controllers;

import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.services.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@AllArgsConstructor
@Tag(name = "Home API", description = "Endpoints for searching and browsing courses on the home page")
public class HomeController {

    private final HomeService homeService;

    @Operation(
            summary = "Search courses by keyword",
            description = "Search for courses using a keyword with pagination support. "
                    + "Specify the search keyword, page number, and page size."
    )
    @GetMapping("/home/search/{keyword}/{page}/{size}")
    public ResponseEntity<Page<CourseDb>> searchCourse(
            @PathVariable("keyword") String keyword,
            @PathVariable("page") int page,
            @PathVariable("size") int size) {
        return new ResponseEntity<>(homeService.searchCourse(keyword, page, size), HttpStatus.OK);
    }
}
