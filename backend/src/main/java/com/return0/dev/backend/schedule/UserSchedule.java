package com.return0.dev.backend.schedule;

import com.return0.dev.backend.bussiness.EmailBusiness;
import com.return0.dev.backend.exception.BaseException;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserSchedule {

    private EmailBusiness emailBusiness;

    public UserSchedule(EmailBusiness emailBusiness) {
        this.emailBusiness = emailBusiness;
    }

    // 1 => second
    // 2 => minute
    // 3 => hour
    // 4 => day
    // 5 => month
    // 6 => year

//    @Scheduled(cron = "0 * * * * *")
    public void testEveryMinute() throws BaseException {
        emailBusiness.sendActivateUserEmail(TestData.email, TestData.name, TestData.token);

        log.info("Hello, Sussss");
    }

//    @Scheduled(cron = "0 0 0 * * *")
    public void testEveryMidnight() throws BaseException {
        emailBusiness.sendActivateUserEmail(TestData.email, TestData.name, TestData.token);

        log.info("Hello, Everyday at 00.00");
    }

    /**
     *
     * Everyday at 10:50
     */
//    @Scheduled(cron = "0 50 10 * * *", zone = "Asia/Bangkok")
    public void testEveryTenAm() throws BaseException {
        emailBusiness.sendActivateUserEmail(TestData.email, TestData.name, TestData.token);

        log.info("Hello, Everyday at 10:50");
    }

    interface TestData{
        String email = "phoomminpaizyl4@gmail.com";
        String name = "phoommin naksenee";
        String token = "test#pewEmpi";
    }
}
