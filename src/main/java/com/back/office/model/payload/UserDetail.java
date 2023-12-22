package com.back.office.model.payload;

import com.back.office.enums.KycStatus;
import lombok.Data;


@Data
public class UserDetail {
    private String email;
    private String phoneNumber ;
    private KycStatus kycStatus;
    private String auditTrail ;
    private String eSignedPdf;
    private String documentId ;
    private String clientCode;
}
