package com.return0.dev.backend.bussiness;

import com.return0.dev.backend.exception.BaseException;
import com.return0.dev.backend.exception.EmailException;
import com.return0.dev.common.EmailRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Service
@Log4j2
public class EmailBusiness {


    private final KafkaTemplate<String, EmailRequest> kafkaTemplate;


    public EmailBusiness(KafkaTemplate<String, EmailRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendActivateUserEmail(String email, String name, String token) throws BaseException {
        // prepare content
        String html = "";
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (IOException e) {
            throw EmailException.templateNotFound();
        }

        String finalLink = "http://localhost:4200/activate/" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${P_LINK}", finalLink);

        EmailRequest request = new EmailRequest();
        request.setTo(email);
        request.setSubject("please activate your account");
        request.setContent(html);

        CompletableFuture<SendResult<String, EmailRequest>> send = kafkaTemplate.send("activation-email", request);
        send.whenComplete(new BiConsumer<SendResult<String, EmailRequest>, Throwable>() {
            @Override
            public void accept(SendResult<String, EmailRequest> stringStringSendResult, Throwable throwable) {
                log.info("sendSuccess");
                log.info(stringStringSendResult);
            }
        });
    }

    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));
    }
}
