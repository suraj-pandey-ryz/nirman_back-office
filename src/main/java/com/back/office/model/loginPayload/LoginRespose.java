package com.back.office.model.loginPayload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRespose {
    private String message;
}
