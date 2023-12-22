package com.back.office.model.payload.dto;

import com.back.office.model.dao.AddressDao;
import lombok.Builder;
import lombok.Data;

@Data
public class AadharData {
    private String maskAadharNumber;
    private String name;
    private String dob;
    private String timeStampGeneratedTimeActual;
    private String timeToLiveActual;
    private String downloadedTimeInternal;
    private String careOf;
    private String aadharXml;
    private String aadharProfilePhotoBase64;
    private AddressDto aadharAddressDto;
}
