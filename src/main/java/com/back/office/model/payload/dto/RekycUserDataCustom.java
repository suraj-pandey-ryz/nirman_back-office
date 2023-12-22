package com.back.office.model.payload.dto;

import com.back.office.model.payload.dto.BasicDetailsDto.NomineeDto;
import com.back.office.model.payload.dto.CustomeBasicDto.BankDetails;
import com.back.office.model.payload.dto.CustomeBasicDto.PanData;
import com.back.office.model.rekyc.RekycBackOfficeDetails;

import lombok.Data;

@Data
public class RekycUserDataCustom {
    private RekycBackOfficeDetails backOfficeDetails;
    private AadharData aadharData;
    private PanData panData;
    private NomineeDto nomineeDto;
    private BankDetails bankDetails;
}
