package com.return0.dev.backend;

import com.return0.dev.backend.bussiness.EmailBusiness;
import com.return0.dev.backend.entity.Address;
import com.return0.dev.backend.entity.Social;
import com.return0.dev.backend.entity.User;
import com.return0.dev.backend.exception.BaseException;
import com.return0.dev.backend.exception.UserException;
import com.return0.dev.backend.service.AddressService;
import com.return0.dev.backend.service.SocialService;
import com.return0.dev.backend.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestEmailBusiness {

    @Autowired
    private EmailBusiness emailBusiness;

    @Order(1)
    @Test
    void testSendActivateEmail() throws BaseException {
        emailBusiness.sendActivateUserEmail(TestData.email, TestData.name, TestData.token);
    }

    interface TestData{
        String email = "phoomminpaizyl4@gmail.com";
        String name = "phoommin naksenee";
        String token = "test#pewEmpi";
    }
}
