package com.app.LearningPlatformAPI.services;


import com.app.LearningPlatformAPI.dto.LoginDto;
import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.AdminDb;
import com.app.LearningPlatformAPI.entities.MasterDb;
import com.app.LearningPlatformAPI.entities.UserDb;
import com.app.LearningPlatformAPI.repository.AdminRepository;
import com.app.LearningPlatformAPI.repository.MasterRepository;
import com.app.LearningPlatformAPI.repository.UserRepository;
import com.app.LearningPlatformAPI.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private AdminRepository adminRepository;
    private MasterRepository masterRepo;


    public ResponseDto SignUp(UserDb userDb){

        Optional<UserDb> isExist = userRepo.findByEmail(userDb.getEmail());
        Optional<AdminDb> isAdmin = adminRepository.findByEmail(userDb.getEmail());
        Optional<MasterDb> isMaster = masterRepo.findByEmail(userDb.getEmail());


        if(isExist.isPresent() || isAdmin.isPresent() || isMaster.isPresent()){
            throw new RuntimeException("The email is already exist");
        } else{
            userDb.setPassword(passwordEncoder.encode(userDb.getPassword()));
            userRepo.save(userDb);
            return new ResponseDto("Register Successfully", "Register");
        }
    }

    public ResponseDto SignIn(LoginDto login) {

        Optional<UserDb> userOpt = userRepo.findByEmail(login.getEmail());
        Optional<AdminDb> adminOpt = adminRepository.findByEmail(login.getEmail());
        Optional<MasterDb> masterOpt = masterRepo.findByEmail(login.getEmail());

        // Check if user or admin exists
        if (userOpt.isEmpty() && adminOpt.isEmpty() && masterOpt.isEmpty()) {
            throw new RuntimeException("Email does not exist");
        }

        // Check password based on account type
        if (userOpt.isPresent()) {
            UserDb user = userOpt.get();
            if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }
        } else if(adminOpt.isPresent()){
            AdminDb admin = adminOpt.get();
            if (!passwordEncoder.matches(login.getPassword(), admin.getPassword())) {
                throw new RuntimeException("Invalid password");
            }
        }
        else{
            MasterDb master = masterOpt.get();
            if (!passwordEncoder.matches(login.getPassword(),master.getPassword())) {
                throw new RuntimeException("Invalid password");
            }
        }

        // Generate JWT token
        String token = jwtUtil.generateJwtToken(login.getEmail());
        return new ResponseDto("Login Successful", "LoggedIn", Optional.ofNullable(token));
    }



    public ResponseDto RegisterAdmin(AdminDb adminDb){

        Optional<AdminDb> fetch = adminRepository.findByEmail(adminDb.getEmail());
        Optional<UserDb> fetchUser = userRepo.findByEmail(adminDb.getEmail());

        if(fetch.isPresent() || fetchUser.isPresent()){
            throw new RuntimeException("this user is already exist");
        }
        else{
            adminDb.setPassword(passwordEncoder.encode(adminDb.getPassword()));
            adminRepository.save(adminDb);
            return new ResponseDto("Register admin successfully","Register");
        }
    }

    public ResponseDto isLoggedIn(HttpServletRequest request){

        String pureToken = "";

        for(Cookie cookie : request.getCookies()){
            if("jwt".equals(cookie.getName())){
                pureToken = cookie.getValue();
            }
        }

        String email =jwtUtil.decodeToken(pureToken).getSubject();

        Optional<UserDb> user = userRepo.findByEmail(email);
        Optional<AdminDb> adminDb = adminRepository.findByEmail(email);

        if(user.isPresent()) {
            return new ResponseDto("User is LoggedIn", "LoggedIn", Optional.of(user.get().getRole()));
        }
        else{
            return new ResponseDto("Admin is LoggedIn", "LoggedIn", Optional.of(adminDb.get().getRole()));
        }
    }
}
