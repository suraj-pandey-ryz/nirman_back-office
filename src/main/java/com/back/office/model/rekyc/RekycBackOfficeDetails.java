package com.back.office.model.rekyc;

import com.back.office.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RekycBackOfficeDetails {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String fatherName;
    private Gender gender;
    private String panNumber;
}
