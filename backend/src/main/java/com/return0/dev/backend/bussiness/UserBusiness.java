package com.return0.dev.backend.bussiness;

import com.return0.dev.backend.entity.User;
import com.return0.dev.backend.exception.BaseException;
import com.return0.dev.backend.exception.FileException;
import com.return0.dev.backend.exception.UserException;
import com.return0.dev.backend.mapper.UserMapper;
import com.return0.dev.backend.model.*;
import com.return0.dev.backend.service.TokenService;
import com.return0.dev.backend.service.UserService;
import com.return0.dev.backend.utils.SecurityUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserBusiness {

    private final UserMapper userMapper;
    private final UserService userService;
    private final TokenService tokenService;

    private final EmailBusiness emailBusiness;

    public UserBusiness(UserService userService, UserMapper userMapper, TokenService tokenService, EmailBusiness emailBusiness) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.emailBusiness = emailBusiness;

    }

    public MLoginResponse login(MLoginRequest request) throws BaseException {
        MLoginResponse response = new MLoginResponse();
        // validate request

        // verify db
        Optional<User> opt = userService.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            throw UserException.loginFailEmailNotFound();
        }

        User user = opt.get();

        if (!userService.matchPassword(request.getPassword(), user.getPassword())) {
            throw UserException.loginFailPasswordIncorrect();
        }

        // verify isActivated
        if (!user.isActivated()) {
            throw UserException.loginFailUnactivated();
        }

        String token = tokenService.tokenize(user);
        response.setCode("S0000");
        response.setEmail(request.getEmail());
        response.setUser_id(user.getId());
        response.setToken(token);

        return response;
    }

    public MUserProfile getUserProfile() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();
        Optional<User> optUser = userService.findById(userId);

        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();

        return userMapper.toUserProfile(user);
    }


    public MRegisterResponse register(MRegisterRequest request) throws BaseException {
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName(), SecurityUtil.generateToken());

        sendEmail(user);

        return userMapper.toRegisterRespone(user);
    }

    public MUpdateProfileResponse updateUserProfile(MUpdateProfileRequest request) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        if (ObjectUtils.isEmpty(request.getName())) {
            throw UserException.nameNull();
        }

        String userId = opt.get();
        userService.updateName(userId, request.getName());

        MUpdateProfileResponse response = new MUpdateProfileResponse();
        response.setCode("200");
        response.setName(request.getName());

        return response;
    }

    public MActivateResponse activate(MActivateRequest request) throws BaseException {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.activateTokenisNull();
        }

        Optional<User> opt = userService.findByToken(token);
        if (opt.isEmpty()) {
            throw UserException.activateTokenFailed();
        }

        User user = opt.get();
        Date now = new Date();
        Date expDate = user.getTokenExp();

        if (now.after(expDate)) {
            throw UserException.activateTokenExp();
        }

        // UPDATE: filed isActivated in DB
        userService.updateActivated(user.getId());

        MActivateResponse response = new MActivateResponse();
        response.setActivated(true);

        return response;
    }

    public MResendActivateResponse ResendActivate(MResendActivateRequest request) throws BaseException {
        String token = request.getToken();

        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.activateTokenisNull();
        }

        Optional<User> opt = userService.findByToken(token);
        if (opt.isEmpty()) {
            throw UserException.resendActTokenNotMatch();
        }

        User user = opt.get();

        String newToken = SecurityUtil.generateToken();

        // UPDATE: filed token in DB
        userService.updateToken(user.getId(), newToken);

        sendEmail(user);

        MResendActivateResponse response = new MResendActivateResponse();
        response.setCode("200");
        response.setStatus("we send new token in your email");

        return response;
    }

    private void sendEmail(User user) throws BaseException {
        String token = user.getToken();

        try {
            emailBusiness.sendActivateUserEmail(user.getEmail(), user.getName(), token);
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();
        Optional<User> optUser = userService.findById(userId);

        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();

        return tokenService.tokenize(user);
    }

    public String uploadPicture(MultipartFile file) throws BaseException {
        // validate file
        if (file == null) {
            // throw error
            throw FileException.fileNull();
        }

        // validate size
        if (file.getSize() > 1048576 * 2) {
            // throw error
            throw FileException.fileMaxSize();
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            // throw error
            throw FileException.unsupported();
        }

        List<String> supportType = Arrays.asList("image/jpeg", "image/png");
        if (!supportType.contains(contentType)) {
            // throw error unsupported
            throw FileException.unsupported();
        }

        // TODO: upload file File Storage (AWS S3, etc..)
        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    public void deleteUser() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();
        userService.deleteById(userId);

    }

}
