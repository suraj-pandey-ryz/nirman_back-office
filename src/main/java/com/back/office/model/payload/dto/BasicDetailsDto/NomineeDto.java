package com.back.office.model.payload.dto.BasicDetailsDto;

import com.back.office.model.payload.dto.AddressDto;
import lombok.Data;

@Data
public class NomineeDto {
    private String name ;
    private String dateOfBirth;
    private String contactNumber ;
    private String email ;
    private String relation ;
    private String proofOfAddress ;
    private String typeOfDocument;
    private String poiReferenceNumber;
    private AddressDto addressDto ;
    private String sharePercentage ;
}
