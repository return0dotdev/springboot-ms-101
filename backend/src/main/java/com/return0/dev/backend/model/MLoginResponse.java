package com.return0.dev.backend.model;

import lombok.Data;

@Data
public class MLoginResponse extends Response {
    private String email;
    private String user_id;
    private String token;
}
