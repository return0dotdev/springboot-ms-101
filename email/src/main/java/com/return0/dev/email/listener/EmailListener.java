package com.return0.dev.email.listener;

import com.return0.dev.common.EmailRequest;
import com.return0.dev.email.service.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class EmailListener {

    private final EmailService emailService;

    public EmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "activation-email")
    public void listenForActivationEmail (EmailRequest request){
        log.info("Kafka received: " + request.getTo());
        emailService.send(request.getTo(), request.getSubject(), request.getContent());
    }
}
