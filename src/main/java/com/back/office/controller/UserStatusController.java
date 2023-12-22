package com.back.office.controller;

import com.back.office.model.payload.UserDataBox;
import com.back.office.model.payload.dto.CommonResponse;
import com.back.office.model.payload.dto.RejectedDocumentsData;
import com.back.office.service.UserStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/admin-ekyc/")
public class UserStatusController {
    private final UserStatusService userStatusService;

    public UserStatusController(UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    @GetMapping("/status")
    public ResponseEntity<?> getUserStatus(){
        return new ResponseEntity<>( userStatusService.userStatus() ,HttpStatus.ACCEPTED);
    }
    @GetMapping("/ping")
    public ResponseEntity<?> ping(){
        log.info("-------------------------pinging on admin office ----------------------");
        return ResponseEntity.ok("ping ");
    }
    @GetMapping("user-data/{phoneNumber}")
    public ResponseEntity<UserDataBox> userDetails(@PathVariable(name = "phoneNumber") String phoneNumber){
        return new ResponseEntity<>(userStatusService.getDataOfUser(phoneNumber), HttpStatus.ACCEPTED);
    }
    @PostMapping("user/upload/document/{phoneNumber}")
    public ResponseEntity<CommonResponse> uploadRejectedDocuments(@PathVariable(name = "phoneNumber") String phoneNumber , @RequestBody RejectedDocumentsData rejectedDocumentsData) {
        return new ResponseEntity<>(userStatusService.uploadUserRejectedDocument(phoneNumber,rejectedDocumentsData), HttpStatus.ACCEPTED);
    }
}
