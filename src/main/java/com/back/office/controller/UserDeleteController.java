package com.back.office.controller;

import com.back.office.service.UserDeleteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/delete/")
public class UserDeleteController {
    private  final UserDeleteService userDeleteService;

    public UserDeleteController(UserDeleteService userDeleteService) {
        this.userDeleteService = userDeleteService;
    }
    @DeleteMapping("user/phone/{phoneNumber}")
    public ResponseEntity<?> delteUserDetails(@PathVariable(name = "phoneNumber") String phoneNumber) {
        return ResponseEntity.ok(userDeleteService.deleteUserPhoneNumber(phoneNumber));
    }
}
