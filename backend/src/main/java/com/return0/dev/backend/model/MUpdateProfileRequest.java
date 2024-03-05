package com.return0.dev.backend.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@RequestMapping
public class MUpdateProfileRequest {
    private String name;
}
