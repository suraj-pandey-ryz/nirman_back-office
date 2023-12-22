package com.back.office.service.rekyc;

import com.back.office.enums.Gender;
import com.back.office.enums.RekycStatus;
import com.back.office.exception.BusinessException;
import com.back.office.exception.ErrorCodes;
import com.back.office.model.dao.rekyc.RekycUserDetailsDao;
import com.back.office.model.payload.dto.AadharData;
import com.back.office.model.payload.dto.BasicDetailsDto.NomineeDto;
import com.back.office.model.payload.dto.CustomeBasicDto.BankDetails;
import com.back.office.model.payload.dto.CustomeBasicDto.PanData;
import com.back.office.model.payload.dto.RekycUserDataCustom;
import com.back.office.model.payload.filter.PageDetail;
import com.back.office.model.rekyc.RekycBackOfficeDetails;
import com.back.office.model.rekyc.RekycUserBasicAdminContent;
import com.back.office.model.rekyc.RekycUserBasicWrapperContent;
import com.back.office.repository.RekycUserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RekycUserDetailsService {
    private final RekycUserRepository rekycUserRepository;
    private final RekycBackOfficeDetailsService rekycBackOfficeDetailsService;
    private final ObjectMapper objectMapper;
    private final int pageSize = 10 ;

    public RekycUserDetailsService(RekycUserRepository rekycUserRepository, RekycBackOfficeDetailsService rekycBackOfficeDetailsService, ObjectMapper objectMapper) {
        this.rekycUserRepository = rekycUserRepository;
        this.rekycBackOfficeDetailsService = rekycBackOfficeDetailsService;
        this.objectMapper = objectMapper;
    }

    public RekycUserBasicWrapperContent getFilterListOfRekycUser(String searchedText , Integer pageNumber, RekycStatus rekycStatus) {
        if(Objects.isNull(pageNumber) || pageNumber < 1) {
            pageNumber = 1 ;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize, Sort.by("updatedAt").descending());
        if(StringUtils.isEmpty(searchedText)) searchedText = "";
        Page<RekycUserDetailsDao> pageOfUserDetails = rekycCustomFilterAndSearchDetailsOfRekycUser(searchedText, pageRequest, rekycStatus);
        List<RekycUserDetailsDao> userDetailedList = pageOfUserDetails.getContent();
        List<RekycUserBasicAdminContent> rekycUserContent = new ArrayList<>();
        for(RekycUserDetailsDao rekycUser : userDetailedList) {
            RekycUserBasicAdminContent rekycUserBasicAdminContent = new RekycUserBasicAdminContent();
            rekycUserBasicAdminContent.setName(rekycUser.getName());
            rekycUserBasicAdminContent.setPanNumber(rekycUser.getEmail());
            rekycUserBasicAdminContent.setPhoneNumber(rekycUser.getMobile());
            rekycUserBasicAdminContent.setClientCode(rekycUser.getClientCode());
            rekycUserBasicAdminContent.setUpdateDate(String.valueOf(rekycUser.getUpdatedAt()));
            // boolean value are by default false
            if(!StringUtils.isEmpty(rekycUser.getMobile())) rekycUserBasicAdminContent.setMobileChanged(true);
            if(!StringUtils.isEmpty(rekycUser.getEmail())) rekycUserBasicAdminContent.setEmailChanged(true);
            if(!Objects.isNull(rekycUser.getAadharDataDao())) rekycUserBasicAdminContent.setAddressChanged(true);
            if(!Objects.isNull(rekycUser.getBankDetailsDao())) rekycUserBasicAdminContent.setBankChanged(true);
            rekycUserBasicAdminContent.setUserid(String.valueOf(rekycUser.getId()));
            rekycUserContent.add(rekycUserBasicAdminContent);
        }
        int totalPage = pageOfUserDetails.getTotalPages();

        PageDetail pageDetail = PageDetail.builder().currentPage(String.valueOf(pageNumber)).pageSize(String.valueOf(pageSize)).totalPage(String.valueOf(totalPage)).totalRecords(String.valueOf(pageOfUserDetails.getTotalElements())).build();

        RekycUserBasicWrapperContent rekycUserBasicWrapperContent = RekycUserBasicWrapperContent.builder().pageDetail(pageDetail).rekycUserBasicAdminContent(rekycUserContent).build();
        return rekycUserBasicWrapperContent;
    }
    private Page<RekycUserDetailsDao> rekycCustomFilterAndSearchDetailsOfRekycUser(String searchedText,PageRequest pageRequest, RekycStatus rekycStatus) {
        return rekycUserRepository.searchUserDetailsInDBAndFilterData(searchedText, rekycStatus, pageRequest);
    }

    public RekycUserDataCustom detailsOfRekycUsers(UUID xuserid) {
        RekycUserDetailsDao rekycUser = rekycUserRepository.findById(xuserid).orElseThrow(() -> new BusinessException(ErrorCodes.REKYC_USER_NOT_FOUND, HttpStatus.BAD_REQUEST));
        RekycUserDataCustom rekycUserDataCustom = new RekycUserDataCustom();
        if (!Objects.isNull(rekycUser.getAadharDataDao())) {
            AadharData aadharData = objectMapper.convertValue(rekycUser.getAadharDataDao(), AadharData.class);
            rekycUserDataCustom.setAadharData(aadharData);
        }
        if(!Objects.isNull(rekycUser.getPanCardDao())) {
            PanData pan = objectMapper.convertValue(rekycUser.getPanCardDao(), PanData.class);
            rekycUserDataCustom.setPanData(pan);
        }
        if(!Objects.isNull(rekycUser.getBankDetailsDao())) {
            BankDetails bank = objectMapper.convertValue(rekycUser.getBankDetailsDao() , BankDetails.class);
            rekycUserDataCustom.setBankDetails(bank);
        }
        if(!Objects.isNull(rekycUser.getNomineeDao())) {
            NomineeDto nomineeDto = objectMapper.convertValue(rekycUser.getNomineeDao(),  NomineeDto.class);
            rekycUserDataCustom.setNomineeDto(nomineeDto);
        }
        // custom setting values from backOffice data
        String response = rekycBackOfficeDetailsService.backOfficeDetailsOfUser(rekycUser.getClientCode());
        JsonNode rootNode= null;
        try {
             rootNode = objectMapper.readTree(response);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodes.BACK_OFFICE_DATA_CANNOT_BE_READ, HttpStatus.BAD_REQUEST);
        }
        RekycBackOfficeDetails rekycBackOfficeDetails = new RekycBackOfficeDetails();
        rekycBackOfficeDetails.setEmail(rootNode.get(0).get("emailno").asText());
        rekycBackOfficeDetails.setName(rootNode.get(0).get("name").asText());
        rekycBackOfficeDetails.setFatherName(rootNode.get(0).get("fathername").asText());
        rekycBackOfficeDetails.setPanNumber(rootNode.get(0).get("panno").asText());
        rekycBackOfficeDetails.setPhoneNumber(rootNode.get(0).get("mobileno").asText());
        rekycBackOfficeDetails.setGender(((rootNode.get(0).get("gender").asText()).equals("F")) ? Gender.FEMALE: Gender.MALE);
        rekycUserDataCustom.setBackOfficeDetails(rekycBackOfficeDetails);
        rekycUserDataCustom.getBackOfficeDetails().setUserId(xuserid.toString());
        return rekycUserDataCustom;
    }
}
