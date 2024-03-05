package com.return0.dev.backend.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@RequestMapping
public class MUpdateProfileResponse extends Response {
    private String name;
}
