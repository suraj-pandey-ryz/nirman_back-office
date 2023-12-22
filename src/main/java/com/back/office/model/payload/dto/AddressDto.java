package com.back.office.model.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
        private String houseNumber ;
        private String location ;
        private String street ;
        private String votingCenter ;
        private String district ;
        private String pincode ;
        private String state ;
        private String country ;
}
