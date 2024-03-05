package com.return0.dev.common;

import lombok.Data;

@Data
public class EmailRequest {

    private String to;

    private String Subject;

    private String content;

}
