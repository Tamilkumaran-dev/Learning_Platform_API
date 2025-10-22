package com.app.LearningPlatformAPI.controllers;


import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/check")
public class IsLogInController {

    private AuthService authService;

    @GetMapping("/isLoggedIn")
    public ResponseEntity<ResponseDto> isLoggedin(HttpServletRequest request){
        return new ResponseEntity<>(authService.isLoggedIn(request), HttpStatus.OK);
    }

}
