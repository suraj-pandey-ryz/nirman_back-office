package com.back.office.model.payload.dto.CustomeBasicDto;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
public class BankDetails {
    private String bankRegisteredName ;
    private String ifsc ;
    private String accountNumber ;
    private String payoutId ;
    private String micr ;
    private String bankName ;
    private String branch;
    private String pennyDropReason;
    private String address ;
    private String status ;
    private String accountType;
    private String bankValidationProof;
}
