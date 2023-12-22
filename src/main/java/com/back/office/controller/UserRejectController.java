package com.back.office.controller;


import com.back.office.enums.RejectDocument;
import com.back.office.model.payload.notification.Notification;
import com.back.office.service.UserRejectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/reject")
public class UserRejectController {
    private final UserRejectService userRejectService;
    public UserRejectController(UserRejectService userRejectService) {
        this.userRejectService = userRejectService;
    }
    @PostMapping("/send/rejection/mail/{phoneNumber}")
    public ResponseEntity<Notification> sendRejectionMailToUser(@PathVariable(name = "phoneNumber")String phoneNumber, @RequestBody Map<RejectDocument, String> rejectedMessages) {
        return ResponseEntity.ok(userRejectService.sendRejectedNotificationToUser(phoneNumber, rejectedMessages, false));
    }

    @PostMapping("/rekyc/send/rejection")
    public ResponseEntity<Notification> sendRejectionMailToRekycUser(@RequestHeader(name = "xuserid") String xuserid , @RequestBody Map<RejectDocument, String> rejectedMessages ) {
        return ResponseEntity.ok(userRejectService.sendRejectedNotificationToUser(xuserid, rejectedMessages, true));
    }
}
