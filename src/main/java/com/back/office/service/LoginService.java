package com.back.office.service;

import com.back.office.exception.ErrorCodes;
import com.back.office.exception.BusinessException;
import com.back.office.model.LoginRequest;
import com.back.office.model.loginPayload.LoginRespose;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public LoginRespose userAuthentication(LoginRequest loginRequest) {
        if(loginRequest.getEmail().equals("admin@gillbroking.com") && (loginRequest.getPassword().equals("abc123"))) {
            return LoginRespose.builder().message("login successfull").build();
        }
        throw new BusinessException(ErrorCodes.LOGIN_FAIL, HttpStatus.BAD_REQUEST);
    }
}
