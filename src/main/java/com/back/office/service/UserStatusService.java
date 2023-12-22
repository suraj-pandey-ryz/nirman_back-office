package com.back.office.service;

import com.back.office.enums.RejectDocument;
import com.back.office.exception.ErrorCodes;
import com.back.office.exception.BusinessException;
import com.back.office.model.dao.*;
import com.back.office.model.payload.UserDataBox;
import com.back.office.model.payload.UserDetail;
import com.back.office.model.payload.UserAdminPageContent;
import com.back.office.model.dao.BasicDetailsDao.BankDetailsDao;
import com.back.office.model.dao.BasicDetailsDao.BasicUserInfoDao;
import com.back.office.model.dao.BasicDetailsDao.NomineeDao;
import com.back.office.model.payload.dto.AadharData;
import com.back.office.model.payload.dto.AddressDto;
import com.back.office.model.payload.dto.BasicDetailsDto.BasicUserInfoDto;
import com.back.office.model.payload.dto.BasicDetailsDto.NomineeDto;
import com.back.office.model.payload.dto.CommonResponse;
import com.back.office.model.payload.dto.CustomeBasicDto.BankDetails;
import com.back.office.model.payload.dto.CustomeBasicDto.ImageAndSignature;
import com.back.office.model.payload.dto.CustomeBasicDto.PanData;
import com.back.office.model.payload.dto.RejectedDocumentsData;
import com.back.office.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserStatusService {
    private final UserDetailMainRepository userDetailMainRepository;
    private final AadharDataRepository aadharDataRepository ;
    private final PanCardRepository panCardRepository;
    private final ObjectMapper objectMapper;
    private final ImageSignatureRepository imageSignatureRepository;
    private final BankDetailsRepository bankDetailsRepository ;
    private final NomineeDetailRepository nomineeDetailRepository;
    private final BasicUserDetailsRepository basicUserDetailsRepository;
    private final AddressRepository addressRepository ;

    public UserStatusService(UserDetailMainRepository userDetailMainRepository, AadharDataRepository aadharDataRepository, PanCardRepository panCardRepository, ObjectMapper objectMapper, ImageSignatureRepository imageSignatureRepository, BankDetailsRepository bankDetailsRepository, NomineeDetailRepository nomineeDetailRepository, BasicUserDetailsRepository basicUserDetailsRepository, AddressRepository addressRepository) {
        this.userDetailMainRepository = userDetailMainRepository;
        this.aadharDataRepository = aadharDataRepository;
        this.panCardRepository = panCardRepository;
        this.objectMapper = objectMapper;
        this.imageSignatureRepository = imageSignatureRepository;
        this.bankDetailsRepository = bankDetailsRepository;
        this.nomineeDetailRepository = nomineeDetailRepository;
        this.basicUserDetailsRepository = basicUserDetailsRepository;
        this.addressRepository = addressRepository;
    }

    public List<UserAdminPageContent> userStatus() {
        log.info("------------------ user status working ");
        List<UserDetailMainDao> listOfUserDetails =  userDetailMainRepository.findAll();
        List<UserAdminPageContent> listOfUsersStatus  = new ArrayList<>();
        log.info("-------------------------11");
        for (int i = 0; i < listOfUserDetails.size(); i++) {
            UserAdminPageContent status = new UserAdminPageContent();
            log.info("-------------------------11");
            UserDetailMainDao userDetails = listOfUserDetails.get(i);
            status.setPhoneNumber(userDetails.getPhoneNumber());
            String email = userDetails.getEmail();
            log.info("-------------------------11");
            status.setEmail(email != null && !email.isEmpty() ? email : "Not Procced");
            String clientCode = userDetails.getClientCode();
            status.setClientCode(clientCode != null && !clientCode.isEmpty() ? clientCode : "Not Created");
            status.setBranch("Nirman");
            log.info("-------------------------11");
//            status.setDateEntryTime(userDetails.getCreatedAt());
            status.setKycStatus(userDetails.getKycStatus());
            if(listOfUserDetails.get(i).getAdharId() != null){
                Optional<AadharDataDao> aadhar = aadharDataRepository.findById(userDetails.getAdharId());
                if(aadhar.isPresent()){
                    status.setCustomerName(aadhar.get().getName());
                }
            }
            log.info("-------------------------11");
            if(listOfUserDetails.get(i).getPanCardId() != null){
                Optional<PanCardDao> pan = panCardRepository.findById(listOfUserDetails.get(i).getPanCardId());
                if(pan.isPresent()){
                    status.setPanCard(pan.get().getPanNumber());
                }
            }
            if(listOfUserDetails.get(i).getDocumentId() != null){
                status.setAction("verified");
                status.setDocument(listOfUserDetails.get(i).getDocumentId());
            }
            else{
                status.setAction("contact");
            }
            status.setReferenceCode("online");
            listOfUsersStatus.add(status);
            log.info("------------------i is working {}" , i);
            log.info("-------------------------11");
        }
        return listOfUsersStatus ;
    }

    public UserDataBox getDataOfUser(String phoneNumber) {
        Optional<UserDetailMainDao> users = userDetailMainRepository.findByPhoneNumber(phoneNumber);
        if (users.isEmpty()) {
            throw new BusinessException(ErrorCodes.USER_NOT_PERSENT, HttpStatus.BAD_REQUEST);
        }
        log.info("-----------------1");
        UserDataBox userDataBox = new UserDataBox();
        if (users.get().getAdharId() != null) {
            Optional<AadharDataDao> aadhar = aadharDataRepository.findById(users.get().getAdharId());
            if (aadhar.isPresent()) {
                userDataBox.setAadhar(objectMapper.convertValue(aadhar.get(), AadharData.class));
                userDataBox.getAadhar().setAadharXml(aadhar.get().getAadharXml());
                userDataBox.getAadhar().setAadharAddressDto(objectMapper.convertValue(aadhar.get().getAadharAddress(), AddressDto.class));
            }
        }
        log.info("-----------------1");
        if (users.get().getPanCardId() != null) {
            Optional<PanCardDao> panCardDao = panCardRepository.findById(users.get().getPanCardId());
            if (panCardDao.isPresent()) {
                userDataBox.setPan(objectMapper.convertValue(panCardDao.get(), PanData.class));
                userDataBox.getPan().setPan_pdf(Base64.encodeBase64String(panCardDao.get().getPan_pdf()));
            }
        }
        log.info("-----------------1");
        if (users.get().getImageAndSignatureId() != null){
            Optional<ImageSignatureDao> imageAndSignature = imageSignatureRepository.findById(users.get().getImageAndSignatureId());
            if (imageAndSignature.isPresent()) {
                userDataBox.setImageAndSignature(objectMapper.convertValue(imageAndSignature.get(), ImageAndSignature.class));
            }
        }
        log.info("-----------------1");
        if (users.get().getBankDetailId() != null) {
            Optional<BankDetailsDao> bankDetailsDao = bankDetailsRepository.findById(users.get().getBankDetailId());
            if (bankDetailsDao.isPresent()) {
                userDataBox.setBankDetails(objectMapper.convertValue(bankDetailsDao.get(), BankDetails.class));
            }
        }
        if(users.get().getBasicUserInformationId() != null) {
            Optional<BasicUserInfoDao> basicUserInfoDao = basicUserDetailsRepository.findById(users.get().getBasicUserInformationId());
            if(basicUserInfoDao.isPresent()) {
                userDataBox.setUserInformation(objectMapper.convertValue(basicUserInfoDao.get(), BasicUserInfoDto.class));
//                userDataBox.setRejectedDocumentDetails(retrieveUserRejectedData(basicUserInfoDao.get().getRejectedDocument()));
            }
        }

        log.info("---------------------------{}" , users.get().getId());
//
        List<NomineeDto> nomineeList = new ArrayList<>();
        if(!Objects.isNull(users.get().getNomineeOne())) {
            Optional<NomineeDao> nomineeOne = nomineeDetailRepository.findById(users.get().getNomineeOne());
            NomineeDto nomineeDto = objectMapper.convertValue(nomineeOne.get(), NomineeDto.class);
            nomineeDto.setAddressDto(objectMapper.convertValue(nomineeOne.get().getAddressDao(), AddressDto.class));
            nomineeList.add(nomineeDto);
        }
        if(!Objects.isNull(users.get().getNomineeTwo())) {
            Optional<NomineeDao> nomineeTwo = nomineeDetailRepository.findById(users.get().getNomineeTwo());
            NomineeDto nomineeDto = objectMapper.convertValue(nomineeTwo.get(), NomineeDto.class);
            nomineeDto.setAddressDto(objectMapper.convertValue(nomineeTwo.get().getAddressDao(), AddressDto.class));
            nomineeList.add(nomineeDto);
        }
        if(!Objects.isNull(users.get().getNomineeThree())) {
            Optional<NomineeDao> nomineeThree = nomineeDetailRepository.findById(users.get().getNomineeThree());
            NomineeDto nomineeDto = objectMapper.convertValue(nomineeThree.get(), NomineeDto.class);
            nomineeDto.setAddressDto(objectMapper.convertValue(nomineeThree.get().getAddressDao(), AddressDto.class));
            nomineeList.add(nomineeDto);
        }
        userDataBox.setNomineeDetails(nomineeList);
        userDataBox.setUserDetail(objectMapper.convertValue(users.get(), UserDetail.class));
        return userDataBox ;
    }
    private Map<RejectDocument, String> retrieveUserRejectedData(String message) {
        if (Objects.isNull(message)) {
            return Collections.emptyMap(); // Return an empty map if no data is available
        } else {
            try {
                Map<String, Object> dataMap = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {
                });
                Map<RejectDocument, String> rejectDocumentList = new HashMap<>();
                for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                    rejectDocumentList.put(RejectDocument.valueOf(entry.getKey()), (String) entry.getValue());
                }
                return rejectDocumentList;
            } catch (Exception e) {
                log.info("-------------- exception on retrival Serlizable data ");
            }
        }
        return Collections.emptyMap();
    }

    public CommonResponse uploadUserRejectedDocument(String phoneNumber , RejectedDocumentsData rejectedDocumentsData) {
        Optional<UserDetailMainDao> users = userDetailMainRepository.findByPhoneNumber(phoneNumber);
        if(users.isEmpty())
            throw new BusinessException(ErrorCodes.USER_NOT_PERSENT, HttpStatus.BAD_REQUEST);
        if(!Objects.isNull(rejectedDocumentsData.getBankProof())) {
            if(Objects.isNull(users.get().getBankDetailId())) {
                throw  new BusinessException(ErrorCodes.USER_NOT_PERSENT, HttpStatus.BAD_REQUEST);
            }
            log.info("------------------------------------- bank proof saving ");
            BankDetailsDao bank = bankDetailsRepository.findById(users.get().getBankDetailId()).orElseThrow(()-> new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST));
            bank.setBankValidationProof(rejectedDocumentsData.getBankProof());
            bankDetailsRepository.save(bank);
        }
        if(!Objects.isNull(rejectedDocumentsData.getClientImage())) {
            if(Objects.isNull(users.get().getImageAndSignatureId())) {
                throw  new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
            log.info("------------------------------------- client image saving");
            ImageSignatureDao imageSignatureDao = imageSignatureRepository.findById(users.get().getImageAndSignatureId()).orElseThrow(()-> new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST));
            imageSignatureDao.setImage(rejectedDocumentsData.getClientImage());
            imageSignatureRepository.save(imageSignatureDao);
        }
        if(!Objects.isNull(rejectedDocumentsData.getClientSignature())) {
            if(Objects.isNull(users.get().getImageAndSignatureId())) {
                throw  new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
            log.info("------------------------------------- client signature saving");
            ImageSignatureDao imageSignatureDao = imageSignatureRepository.findById(users.get().getImageAndSignatureId()).orElseThrow(()-> new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST));
            imageSignatureDao.setSignature(rejectedDocumentsData.getClientSignature());
            imageSignatureRepository.save(imageSignatureDao);
        }
        if(!Objects.isNull(rejectedDocumentsData.getFinancialProof())) {
            if(Objects.isNull(users.get().getBasicUserInformationId())) {
                throw  new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
            log.info("------------------------------------- financial proof saving ");
            BasicUserInfoDao basicUser = basicUserDetailsRepository.findById(users.get().getBasicUserInformationId()).orElseThrow(()-> new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST));
            basicUser.setFinancialProofDocument(rejectedDocumentsData.getFinancialProof());
            basicUserDetailsRepository.save(basicUser);
        }
        if(!Objects.isNull(rejectedDocumentsData.getPanCard())) {
            if(Objects.isNull(users.get().getPanCardId())) {
                throw  new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
            log.info("------------------------------------- pan card saving ");
            PanCardDao pan = panCardRepository.findById(users.get().getPanCardId()).orElseThrow(()->new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST));
            pan.setPan_pdf(java.util.Base64.getDecoder().decode(rejectedDocumentsData.getPanCard()));
            panCardRepository.save(pan);
        }
        return new CommonResponse("success");
    }
}
