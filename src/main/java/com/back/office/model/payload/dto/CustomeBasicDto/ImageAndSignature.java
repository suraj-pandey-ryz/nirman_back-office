package com.back.office.model.payload.dto.CustomeBasicDto;

import lombok.Data;


@Data
public class ImageAndSignature {
    private String image ;
    private String signature ;
    private String longitude;
    private String latitude;
    private String createdAt;
    private String address ;
}
