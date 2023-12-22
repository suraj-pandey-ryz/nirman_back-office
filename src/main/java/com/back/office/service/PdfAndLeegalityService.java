package com.back.office.service;

import com.back.office.enums.NotificationType;
import com.back.office.enums.TemplateType;
import com.back.office.model.payload.PdfFile;
import com.back.office.model.payload.dto.CommonResponse;
import com.back.office.model.payload.notification.Notification;
import com.back.office.notifications.NotificationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PdfAndLeegalityService {
    private final LeegalityService leegalityService ;
    private final PdfConverter pdfConverter;
    private final NotificationService notificationService;


    public PdfAndLeegalityService(LeegalityService leegalityService, PdfConverter pdfConverter, NotificationService notificationService) {
        this.leegalityService = leegalityService;
        this.pdfConverter = pdfConverter;
        this.notificationService = notificationService;
    }

    public CommonResponse getPdfOfUser(String phoneNumber){
        PdfFile pdfFile = pdfConverter.convertIntoPdf(phoneNumber);
        return new CommonResponse(pdfFile.getPdf());
    }


    public CommonResponse sendPdfToEsignOfLeegality(String phoneNumber){
        PdfFile pdfFile = pdfConverter.convertIntoPdf(phoneNumber);
        String signUrl =  leegalityService.esignPdfusingLegality(pdfFile);
        sentEsignNotificationToUser(pdfFile.getEmail(), pdfFile.getName(), signUrl);
        return new CommonResponse("Link succesfully sended to user");
    }

    private void sentEsignNotificationToUser(String email , String name, String esignUrl) {
        Map<String, String> userValues = new HashMap<>();
        userValues.put("name" , name);
        userValues.put("esignurl", esignUrl);
        notificationService.sendNotification(email, TemplateType.NIRMAN_KYC_ESIGN_NOTIFICATION, NotificationType.EMAIL, userValues);
    }
}
