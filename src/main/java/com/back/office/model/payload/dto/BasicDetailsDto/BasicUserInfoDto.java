package com.back.office.model.payload.dto.BasicDetailsDto;

import lombok.Data;


@Data
public class BasicUserInfoDto {
    private String maritalStatus ;
    private String spouseName ;
    private String motherName ;
    private String fatherName ;
    private String education ;
    private String tradingExperience ;
    private String occupation ;
    private String nomineeCheck ;
    private String taxResidence ;
    private String netWorth ;
    private String annualSalary;
    private String politicallyExposed ;
    private boolean nseCash ;
    private boolean nseFutureAndOption ;
    private boolean nseCds ;
    private boolean nseMcx ;
    private String brokerageAmount;
    private String nationality ;
    private String actionTakenBySebiOrAnyOtherAuthority;
    private String actionTakenBySebiOrAnyOtherAuthorityDescription ;
    private String financialProofDocument;
}
