package com.back.office.model.payload.dto.CustomeBasicDto;

import com.back.office.enums.Gender;
import lombok.Data;

import java.util.UUID;
@Data
public class PanData {
    private String panNumber ;
    private String name ;
    private Gender gender ;
    private String dob ;
    private String pan_pdf ;
}
