package com.back.office.model.rekyc;

import com.back.office.enums.RekycLockStatus;
import com.back.office.enums.RekycStatus;
import lombok.Data;

@Data
public class RekycUserBasicAdminContent {
    private String name;
    private String clientCode;
    private String phoneNumber;
    private RekycLockStatus rekycLockStatus;
    private boolean isMobileChanged;
    private boolean isAddressChanged;
    private boolean isEmailChanged;
    private boolean isBankChanged;
    private RekycStatus rekycStatus; //requested or esign
    private String updateDate;
    private String panNumber;
    private String userid;
}
