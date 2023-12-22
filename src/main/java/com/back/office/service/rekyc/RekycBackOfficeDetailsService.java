package com.back.office.service.rekyc;

import com.back.office.enums.Gender;
import com.back.office.exception.BusinessException;
import com.back.office.exception.ErrorCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RekycBackOfficeDetailsService {
    @Value("${back.office.url}")
    private String backOfficeUrl;
    private OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper;

    public RekycBackOfficeDetailsService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Async
    public String backOfficeDetailsOfUser(String clientCode) {
        Request request = new Request.Builder()
                .url(backOfficeUrl + clientCode)
                .addHeader("Content-Type" ,"application/json")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()){
            if(!response.isSuccessful()) throw new BusinessException(ErrorCodes.BACKOFFICE_NOT_AVAILABLE, HttpStatus.BAD_REQUEST);
            return response.body().string();
        } catch (Exception e) {
            throw new BusinessException(ErrorCodes.BACKOFFICE_NOT_AVAILABLE, HttpStatus.UNAUTHORIZED);
        }
    }
}
