package com.back.office.model.payload;

import com.back.office.enums.RejectDocument;
import com.back.office.model.payload.dto.AadharData;
import com.back.office.model.payload.dto.BasicDetailsDto.BasicUserInfoDto;
import com.back.office.model.payload.dto.BasicDetailsDto.NomineeDto;
import com.back.office.model.payload.dto.CustomeBasicDto.BankDetails;
import com.back.office.model.payload.dto.CustomeBasicDto.ImageAndSignature;
import com.back.office.model.payload.dto.CustomeBasicDto.PanData;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserDataBox {
    private AadharData aadhar;
    private BankDetails bankDetails;
    private ImageAndSignature imageAndSignature;
    private PanData pan;
    private BasicUserInfoDto userInformation ;
    private List<NomineeDto> nomineeDetails ;
    private UserDetail userDetail;
    private Map<RejectDocument, String>  rejectedDocumentDetails;
}
