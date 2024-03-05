package com.return0.dev.backend.api;

import com.return0.dev.backend.bussiness.UserBusiness;
import com.return0.dev.backend.entity.User;
import com.return0.dev.backend.exception.BaseException;
import com.return0.dev.backend.exception.UserException;
import com.return0.dev.backend.model.*;
import com.return0.dev.backend.service.UserService;
import io.netty.util.internal.StringUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserApi {
    private final UserBusiness business;
    private final UserService service;

    public UserApi(UserBusiness business, UserService service) {
        this.business = business;
        this.service = service;
    }

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<MLoginResponse> login(@RequestBody MLoginRequest request) throws BaseException{
        MLoginResponse response = business.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<MRegisterResponse> register(@RequestBody MRegisterRequest request) throws BaseException {
        MRegisterResponse response = business.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RequestMapping("/activate")
    public ResponseEntity<MActivateResponse> activate(@RequestBody MActivateRequest request) throws BaseException {
        MActivateResponse response = business.activate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RequestMapping("/resend-activation")
    public ResponseEntity<MResendActivateResponse> activate(@RequestBody MResendActivateRequest request) throws BaseException {
        MResendActivateResponse response = business.ResendActivate(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        return ResponseEntity.ok(business.refreshToken());
    }

    @GetMapping("/user-profile")
    public ResponseEntity<MUserProfile> getProfile() throws BaseException {
        return ResponseEntity.ok(business.getUserProfile());
    }

    @PutMapping
    @RequestMapping("/update-profile")
    public ResponseEntity<MUpdateProfileResponse> updateProfile(@RequestBody MUpdateProfileRequest request) throws BaseException{
        MUpdateProfileResponse response = business.updateUserProfile(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RequestMapping("/testUpload")
    public ResponseEntity<String> uploadProfilePicture(@RequestPart MultipartFile file) throws BaseException{
        String response = business.uploadPicture(file);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<Void> deleteUser() throws BaseException {
        business.deleteUser();
        return ResponseEntity.ok().build();
    }

}
