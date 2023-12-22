package com.back.office.service;

import com.back.office.exception.BusinessException;
import com.back.office.exception.ErrorCodes;
import com.back.office.model.payload.PdfFile;
import com.back.office.model.payload.dto.CommonResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LeegalityService {
    private OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper;
    private final String leegalityAuthToken;
    private final String leegalityRedirectUrl;

    public LeegalityService(ObjectMapper objectMapper, @Value("${leegality.auth.token}") String leegalityAuthToken, @Value("${leegality.redirect.url}") String leegalityRedirectUrl) {
        this.objectMapper = objectMapper;
        this.leegalityAuthToken = leegalityAuthToken;
        this.leegalityRedirectUrl = leegalityRedirectUrl;
    }

    public String esignPdfusingLegality(PdfFile pdfFile){
        String payload = "{\"file\":{\"name\":\"Re_" + pdfFile.getPanCard() + "\",\"file\":\"" + pdfFile.getPdf() + "\"},\"invitees\":[{\"name\":\"" + pdfFile.getName() + "\",\"email\":\"" + pdfFile.getEmail() + "\",\"phone\":\"" + pdfFile.getMobile() + "\",\"emailNotification\":\"true\",\"phoneNotification\":\"false\",\"webhook\":{\"success\":\""+leegalityRedirectUrl+pdfFile.getMobile()+"\",\"failure\":\""+leegalityRedirectUrl+pdfFile.getMobile()+"\",\"version\":\"2.3\"},\"redirectUrl\":\""+leegalityRedirectUrl+pdfFile.getMobile()+"\",\"signatures\":[{\"type\":\"AADHAAR\",\"config\":{\"authTypes\":[\"OTP\",\"BIO\",\"IRIS\"]}}]}],\"irn\":\"" + pdfFile.getPanCard() + "/RE\"}";
        Request request = new Request.Builder()
                .url("https://app1.leegality.com/api/v2.1/sign/request")
                .addHeader("Content-Type" ,"application/json")
                .addHeader("X-Auth-Token", leegalityAuthToken)
                .post(RequestBody.create(MediaType.parse("application/json"), payload))
                .build();
        Call call = client.newCall(request);
        Response response = null ;
        String jsonResponse = null;
        try {
            response = call.execute();
            jsonResponse = response.body().string();
        }
        catch (Exception e) {
            System.out.println(" i  am an Exception");
        }
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode dataNode = rootNode.get("data");
            String documentId = dataNode.get("documentId").asText();
            JsonNode invitationsNode = dataNode.get("invitations");
            String signUrl = invitationsNode.get(0).get("signUrl").asText();
            System.out.println("------------------------------- Document ID: " + documentId);
            System.out.println("------------------------------- Sign URL: " + signUrl);
            return signUrl;
        } catch(Exception e) {
            System.out.println("--------------------  Exception is here");
            throw new BusinessException(ErrorCodes.E_SIGN_NOT_WORKED, HttpStatus.BAD_REQUEST);
        }
    }
}
