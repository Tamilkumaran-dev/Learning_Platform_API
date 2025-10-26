package com.app.LearningPlatformAPI.services;


import com.app.LearningPlatformAPI.dto.ResponseDto;
import com.app.LearningPlatformAPI.entities.AdminDb;
import com.app.LearningPlatformAPI.entities.CourseDb;
import com.app.LearningPlatformAPI.entities.MasterDb;
import com.app.LearningPlatformAPI.entities.UserDb;
import com.app.LearningPlatformAPI.exception.exceptions.NotFound;
import com.app.LearningPlatformAPI.repository.AdminRepository;
import com.app.LearningPlatformAPI.repository.MasterRepository;
import com.app.LearningPlatformAPI.repository.UserRepository;
import com.app.LearningPlatformAPI.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MasterUserService {

    private UserRepository userRepo;
    private AdminRepository adminRepo;
    private MasterRepository masterRepo;
    private PasswordEncoder passwordEncoder;
    private AdminService adminService;
    private JwtUtil jwtUtil;

    public ResponseDto signUpMaster(MasterDb masterDb){

        Optional<UserDb> user = userRepo.findByEmail(masterDb.getEmail());
        Optional<AdminDb> admin = adminRepo.findByEmail(masterDb.getEmail());
        Optional<MasterDb> master = masterRepo.findByEmail(masterDb.getEmail());

        if(user.isPresent() || admin.isPresent() || master.isPresent()){
            throw new RuntimeException("This email id is already exist");
        }
        else{
            masterDb.setPassword(passwordEncoder.encode(masterDb.getPassword()));
            masterRepo.save(masterDb);
            return new ResponseDto("Master id is created","Registered");
        }

    }

    public ResponseDto getMasterProfile(HttpServletRequest request){

        String pureToken = "";

        for(Cookie cookie : request.getCookies()){
            if(cookie.getName().equals("jwt")){
                pureToken = cookie.getValue();
            }
        }

        if(pureToken.isEmpty()){
            throw new NotFound("Cookie not found");
        }

        else{
            String email = jwtUtil.decodeToken(pureToken).getSubject();

            Optional<MasterDb> masterFetch = masterRepo.findByEmail(email);
            if(masterFetch.isEmpty()){
                throw new RuntimeException("The token is invalid");
            }
            else {
                return new ResponseDto("Master profile","MasterProfile",Optional.of(masterFetch));
            }
        }
    }


    public Page<AdminDb> getUnVerifiedAdmin(String keyword, int page, int size){

        Pageable pageable = PageRequest.of(page - 1,size);

        if(keyword.equals("AllAdmin")){
            return adminRepo.findAll(pageable);
        }
        else {
            return adminRepo.unverifiedAccount(keyword, pageable);
        }
    }

    public ResponseDto getAdmin(Long id){

        Optional<AdminDb> admin = adminRepo.findById(id);
        if(admin.isEmpty()){
            throw new NotFound("Admin not found");
        }
        return new ResponseDto("Admin found","Found",Optional.of(admin.get()));

    }

    public ResponseDto deleteAdmin(Long id) {

        AdminDb admin = adminRepo.findById(id)
                .orElseThrow(() -> new NotFound("Instructor not found"));

        if (!admin.getCourses().isEmpty()) {
            // Delete each course properly
            for (CourseDb course : new ArrayList<>(admin.getCourses())) {
                adminService.dropCourse(course.getId());
            }

            // Clear the admin's course list after deletion
            admin.getCourses().clear();
        }

        // Now delete the admin safely
        adminRepo.delete(admin);

        if (adminRepo.existsById(id)) {
            throw new RuntimeException("Unable to delete the admin user");
        }

        return new ResponseDto("Deleted successfully", "Deleted");
    }

}
