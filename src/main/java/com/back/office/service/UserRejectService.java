package com.back.office.service;

import com.back.office.enums.*;
import com.back.office.exception.BusinessException;
import com.back.office.exception.ErrorCodes;
import com.back.office.model.dao.UserDetailMainDao;
import com.back.office.model.dao.rekyc.RekycUserDetailsDao;
import com.back.office.model.payload.notification.Notification;
import com.back.office.notifications.NotificationService;
import com.back.office.repository.RekycUserRepository;
import com.back.office.repository.UserDetailMainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class UserRejectService {

    private final UserDetailMainRepository userDetailMainRepository;
    private final RekycUserRepository rekycUserRepository;
    private final NotificationService notificationService;
    public UserRejectService(UserDetailMainRepository userDetailMainRepository, RekycUserRepository rekycUserRepository, NotificationService notificationService) {
        this.userDetailMainRepository = userDetailMainRepository;
        this.rekycUserRepository = rekycUserRepository;
        this.notificationService = notificationService;
    }
    public Notification sendRejectedNotificationToUser(String clientIdentity, Map<RejectDocument, String> rejectMessages, boolean isRekyc) {
        if(isRekyc) {
            return sendRejectedNotificationToRekycUser(UUID.fromString(clientIdentity), rejectMessages);
        }
        return sendRejectedNotificationToUser(clientIdentity, rejectMessages);
    }
    private Notification sendRejectedNotificationToUser(String phoneNumber, Map<RejectDocument, String> rejectMessages) {
        UserDetailMainDao user = userDetailMainRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new BusinessException(ErrorCodes.USER_NOT_PERSENT, HttpStatus.BAD_REQUEST));
        user.setKycStatus(KycStatus.REJECTED);
        StringBuilder commaSepratedValue = new StringBuilder();
        // going to all value of map to get all data(key, value) and storing in different way
        Map<String, String> rejectConverted = new HashMap<>();
        for(Map.Entry<RejectDocument , String> entry : rejectMessages.entrySet()) {
            rejectConverted.put(entry.getKey().getValue(), entry.getValue());
            if(!entry.getValue().isEmpty()) {
                commaSepratedValue.append(entry.getKey()).append(",");
            }
        }
        rejectConverted.put("name" , (Objects.isNull(user.getEmail())?"user":user.getEmail()));
        if(StringUtils.isEmpty(user.getEmail())) {
            throw new BusinessException(ErrorCodes.USER_EMAIL_NOT_FOUND_PARTIALLY_UPDATED_USER, HttpStatus.BAD_REQUEST);
        }
        // to remove last comma from method
        if (commaSepratedValue.length() > 0) {
            commaSepratedValue.setLength(commaSepratedValue.length() - 1);
        }
        user.setRejecetdDocsCommaSeprated(commaSepratedValue.toString());
        userDetailMainRepository.save(user);
//        rejectConverted.put("name" , "suraj");
        return notificationService.sendNotification(user.getEmail(), TemplateType.NIRMAN_KYC_REJECTED_EMAIL, NotificationType.EMAIL, rejectConverted);
    }
    private Notification sendRejectedNotificationToRekycUser(UUID rekycId , Map<RejectDocument, String> rejectMessages ) {
       RekycUserDetailsDao rekycUserDetailsDao =  rekycUserRepository.findById(rekycId).orElseThrow(()-> new BusinessException(ErrorCodes.REKYC_USER_NOT_FOUND, HttpStatus.BAD_REQUEST));
       rekycUserDetailsDao.setLockStatus(RekycLockStatus.UNLOCKED);
       rekycUserDetailsDao.setRekycStatus(RekycStatus.REJECTED);
       StringBuilder commaSepratedValue = new StringBuilder();
        Map<String, String> rejectConverted = new HashMap<>();
        for(Map.Entry<RejectDocument , String> entry : rejectMessages.entrySet()) {
            rejectConverted.put(entry.getKey().getValue(), entry.getValue());
            if(!entry.getValue().isEmpty()) {
                commaSepratedValue.append(entry.getKey()).append(",");
            }
        }
        if (commaSepratedValue.length() > 0) {
            commaSepratedValue.setLength(commaSepratedValue.length() - 1);
        }
        rejectConverted.put("name" , rekycUserDetailsDao.getName());
        rekycUserDetailsDao.setRejecetdDocsCommaSeprated(commaSepratedValue.toString());
        rekycUserRepository.save(rekycUserDetailsDao);
        return notificationService.sendNotification(rekycUserDetailsDao.getEmail(), TemplateType.NIRMAN_REKYC_REJECTION_EMAIL, NotificationType.EMAIL, rejectConverted);
    }
}
