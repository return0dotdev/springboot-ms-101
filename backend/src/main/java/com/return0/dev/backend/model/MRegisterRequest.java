package com.return0.dev.backend.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@RequestMapping
public class MRegisterRequest {
    private String email;
    private String name;
    private String password;
}
