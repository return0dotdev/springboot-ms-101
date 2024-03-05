package com.return0.dev.backend.bussiness;

import com.return0.dev.backend.exception.BaseException;
import com.return0.dev.backend.exception.ChatException;
import com.return0.dev.backend.model.ChatMessage;
import com.return0.dev.backend.model.ChatMessageRequest;
import com.return0.dev.backend.utils.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatBusiness {

    private final SimpMessagingTemplate template;

    public ChatBusiness(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void post(ChatMessageRequest request) throws BaseException {

//        Optional<String> opt = SecurityUtil.getCurrentUserId();
//
//        if(opt.isEmpty()){
//            throw ChatException.accessDenied();
//        }

        // TODO: validate message

        final String destination = "/topic/chat";

        ChatMessage payload = new ChatMessage();
//        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());
        template.convertAndSend(destination, payload);
    }
}
