package com.back.office.model.payload;

import com.back.office.enums.KycStatus;
import lombok.Data;

@Data
public class UserAdminPageContent {
    private String clientCode ;
    private KycStatus kycStatus;
    private String phoneNumber ;
    private String email ;
    private String action; // contact or verified
    private String referenceCode; // always online
    private String Branch ; // GILL Broking
    private String customerName ;
    private long dateEntryTime ;
    private String panCard ;
    private String document;
    private long updateTime;
}
