package com.back.office.notifications;

import com.back.office.constant.Constant;
import com.back.office.enums.NotificationType;
import com.back.office.enums.RejectDocument;
import com.back.office.enums.TemplateType;
import com.back.office.exception.BusinessException;
import com.back.office.exception.ErrorCodes;
import com.back.office.model.payload.notification.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class NotificationService {
    private OkHttpClient client = new OkHttpClient();
    private final String baseNotificationUrl ;
    private final String kycclientName;

    public NotificationService(@Value("${notification.service.url}") String baseNotificationUrl,
                               @Value("$(kyc.client.name)") String kycclientName) {
        this.baseNotificationUrl = baseNotificationUrl;
        this.kycclientName = kycclientName;
    }
    public Notification sendNotification(String toUser, TemplateType templateType , NotificationType notificationType , Map<String, String> rejectDocument) {
      return  sendNotificationToUser(toUser, templateType, notificationType, rejectDocument);
    }
    private Notification sendNotificationToUser(String toUser, TemplateType templateType , NotificationType notificationType , Map<String, String> rejectDocument) {
        Notification notificationRequest = Notification.builder()
                .to(toUser)
                .subject("alert")
                .templateType(templateType.getValue())
                .model(rejectDocument)
                .build();
        Request request = new Request.Builder()
                .url(baseNotificationUrl +"?type=" + notificationType.getValue())
                .addHeader("Content-Type" ,"application/json")
                .addHeader("CLIENTNAME", "NIRMAN")
                .post(RequestBody.create(okhttp3.MediaType.parse("application/json"), serializeToJson(notificationRequest)))
                .build();
        try (Response response = (client.newCall(request).execute())) {
            log.info("--------------------- response of client {}", response.body().string());
            System.out.println("response of client" + response.isSuccessful());

            if(!response.isSuccessful()) {
                throw new BusinessException(ErrorCodes.NOTIFICATION_SERVICE_IS_NOT_AVAILABLE, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw new BusinessException(ErrorCodes.NOTIFICATION_SERVICE_IS_NOT_AVAILABLE, HttpStatus.BAD_REQUEST);
        }
        return new Notification(Constant.MAIL_SENT);
    }
    private String serializeToJson(Notification notificationRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(notificationRequest);
        }
        catch (Exception e){
            throw new BusinessException(ErrorCodes.NOTIFICATION_SERVICE_CANNOT_BE_SERLIZED, HttpStatus.BAD_REQUEST);
        }
    }
}
