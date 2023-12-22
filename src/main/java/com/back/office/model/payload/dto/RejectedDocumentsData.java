package com.back.office.model.payload.dto;

import lombok.Data;

@Data
public class RejectedDocumentsData {
    private String clientImage;
    private String clientSignature;
    private String bankProof ;
    private String financialProof ;
    private String panCard;
}
