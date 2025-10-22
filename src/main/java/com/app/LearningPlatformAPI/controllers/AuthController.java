package com.app.LearningPlatformAPI.controllers;


import com.app.LearningPlatformAPI.dto.LoginDto;
import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.AdminDb;
import com.app.LearningPlatformAPI.entities.UserDb;
import com.app.LearningPlatformAPI.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<ResponseDto> signUpUser(@RequestBody UserDb userDb){
        return new ResponseEntity<>(authService.SignUp(userDb), HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<ResponseDto> signInUser(@RequestBody LoginDto login, HttpServletResponse response){

        ResponseDto res = authService.SignIn(login);

        if(res.getData().isPresent()){
            System.out.println(res.getData());

            ResponseCookie cookie = ResponseCookie.from("jwt", (String) res.getData().get())
                    .httpOnly(true)          // inaccessible to JS
                    .secure(true)            // required for HTTPS
                    .path("/")               // root path
                    .maxAge(60*120)    // 12 hours
                    .sameSite("none")        // allow cross-origin
                    .build();


            response.addHeader("Set-Cookie", cookie.toString());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/adminSignUp")
    public ResponseEntity<ResponseDto> signUpAdmin(@RequestBody AdminDb adminDb){
        return  new ResponseEntity<>(authService.RegisterAdmin(adminDb),HttpStatus.CREATED);
    }

}
