package com.back.office.service;

import com.back.office.enums.Gender;
import com.back.office.enums.TemplateType;
import com.back.office.exception.BusinessException;
import com.back.office.exception.ErrorCodes;
import com.back.office.model.dao.*;
import com.back.office.model.dao.BasicDetailsDao.BankDetailsDao;
import com.back.office.model.dao.BasicDetailsDao.BasicUserInfoDao;
import com.back.office.model.dao.BasicDetailsDao.NomineeDao;
import com.back.office.model.payload.PdfFile;
import com.back.office.repository.*;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class PdfConverter {

    private final Configuration freeMarkerConfig;
    private final UserDetailMainRepository userDetailMainRepository ;
    private final AadharDataRepository aadharDataRepository ;
    private final ImageSignatureRepository imageSignatureRepository ;
    private final PanCardRepository panCardRepository;
    private final BasicUserDetailsRepository basicUserDetailsRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final NomineeDetailRepository nomineeDetailRepository;
    private final AddressRepository addressRepository;
    public PdfConverter(Configuration freeMarkerConfig, UserDetailMainRepository userDetailMainRepository, AadharDataRepository aadharDataRepository, ImageSignatureRepository imageSignatureRepository, PanCardRepository panCardRepository, BasicUserDetailsRepository basicUserDetailsRepository, BankDetailsRepository bankDetailsRepository, NomineeDetailRepository nomineeDetailRepository, AddressRepository addressRepository) {
        this.freeMarkerConfig = freeMarkerConfig;
        this.userDetailMainRepository = userDetailMainRepository;
        this.aadharDataRepository = aadharDataRepository;
        this.imageSignatureRepository = imageSignatureRepository;
        this.panCardRepository = panCardRepository;
        this.basicUserDetailsRepository = basicUserDetailsRepository;
        this.bankDetailsRepository = bankDetailsRepository;
        this.nomineeDetailRepository = nomineeDetailRepository;
        this.addressRepository = addressRepository;
    }
    public PdfFile convertIntoPdf(String phoneNumber) {
        UserDetailMainDao user = userDetailMainRepository.findByPhoneNumber(phoneNumber).orElseThrow(()-> new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST));
        log.info("---------------------user details {}", user);
        AadharDataDao aadharDataDao = aadharDataRepository.findById(user.getAdharId()).orElseThrow(()-> new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST));
        log.info("-------------------------- 1");
        PanCardDao pan = panCardRepository.findById(user.getPanCardId()).orElseThrow(()->new BusinessException(ErrorCodes.USER_NOT_REGISTERED , HttpStatus.BAD_REQUEST));
        log.info("-------------------------- 1");
        BasicUserInfoDao basicUser = basicUserDetailsRepository.findById(user.getBasicUserInformationId()).orElseThrow(()->new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST));
        log.info("-------------------------- 1");
        BankDetailsDao bankData = bankDetailsRepository.findById(user.getBankDetailId()).orElseThrow(()-> new BusinessException(ErrorCodes.USER_NOT_REGISTERED, HttpStatus.BAD_REQUEST));
        log.info("-------------------------- 1");
        ImageSignatureDao imageSignature = imageSignatureRepository.findById(user.getImageAndSignatureId()).orElseThrow(()-> new BusinessException(ErrorCodes.USER_PERSONAL_DETAILS_NOT_FOUND, HttpStatus.BAD_REQUEST));
        log.info("-------------------------- 1");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        String fullDate = dtf2.format(now);
        Map<String, String> mp = new HashMap<>();

        mp.put("panNumber", pan.getPanNumber());
        mp.put("date", date);
        mp.put("fullDate", fullDate);
        mp.put("reason", "Account Opening");
        mp.put("name", pan.getName());
        mp.put("dob", pan.getDob());
        mp.put("aadharNumber", aadharDataDao.getMaskAadharNumber());
        mp.put("careOf", aadharDataDao.getCareOf());
        mp.put("aadharName", aadharDataDao.getName());
        mp.put("houseNo", aadharDataDao.getAadharAddress().getHouseNumber());
        mp.put("street", aadharDataDao.getAadharAddress().getStreet());
        mp.put("location", aadharDataDao.getAadharAddress().getLocation());
        mp.put("votingCenter", aadharDataDao.getAadharAddress().getVotingCenter());
        mp.put("district", aadharDataDao.getAadharAddress().getDistrict());
        mp.put("state", aadharDataDao.getAadharAddress().getState());
        mp.put("pincode", aadharDataDao.getAadharAddress().getPincode());
        mp.put("country", aadharDataDao.getAadharAddress().getCountry());
        mp.put("email", user.getEmail());
        mp.put("mobile", user.getPhoneNumber());
        mp.put("clientCode", user.getClientCode());
        mp.put("reason", "Account Opening");
        mp.put("netWorth", basicUser.getNetWorth());
        mp.put("bankRegisteredName", bankData.getBankRegisteredName());
        mp.put("bankName", Objects.isNull(bankData.getBankName())?"NO NAME" : bankData.getBankName());
        mp.put("bankAddress", bankData.getAddress());
        mp.put("branch" , bankData.getBranch());
        mp.put("accountNumber", bankData.getAccountNumber());
        mp.put("micr", bankData.getMicr());
        mp.put("ifscCode", bankData.getIfsc());
        mp.put("aadharTimeStamp", aadharDataDao.getDownloadedTimeInternal());
        mp.put("aadharPhoto", aadharDataDao.getAadharProfilePhotoBase64());
        mp.put("gender", pan.getGender().getValue());
        mp.put("fatherName", basicUser.getFatherName());
        mp.put("userSignature", imageSignature.getSignature());
        mp.put("userPicture", imageSignature.getImage());
        mp.put("motherName", basicUser.getMotherName());

        if (basicUser.getMaritalStatus().equals("SINGLE")) {
            mp.put("single", "checked");
            mp.put("married", "unchecked");
        } else {
            mp.put("single", "unchecked");
            mp.put("married", "checked");
        }

        log.info(" check this-------------------{}", pan.getGender());
        if (pan.getGender().equals(Gender.MALE)) {
            mp.put("male", "checked");
            mp.put("female", "unchecked");
            mp.put("transgender", "unchecked");
        } else if (pan.getGender().equals(Gender.FEMALE)) {
            mp.put("male", "unchecked");
            mp.put("female", "checked");
            mp.put("transgender", "unchecked");
        } else {
            mp.put("male", "unchecked");
            mp.put("female", "unchecked");
            mp.put("transgender", "checked");
        }


        // occupation details of ekyc user
        log.info("-------------------- occupation details of user --- {}", user.getPhoneNumber() );
        mp.put("privateSector", "unchecked");
        mp.put("publicSector", "unchecked");
        mp.put("governmentSector", "unchecked");
        mp.put("businessOccupation", "unchecked");
        mp.put("professionalOccupation", "unchecked");
        mp.put("agriculturistOccupation" , "unchecked");
        mp.put("retiredOccupation", "unchecked");
        mp.put("forexDealerOccupation", "unchecked");
        mp.put("houseWifeOccupation" , "unchecked");
        mp.put("studentOccupation", "unchecked");
        mp.put("othersOccupation", "unchecked");

        log.info("-------------------- basic details of user --- {}", user.getPhoneNumber() );
        if(basicUser.getOccupation().equals("Private Sector")) {
            mp.put("privateSector", "checked");
        } else if(basicUser.getOccupation().equals("Public Sector")) {
            mp.put("publicSector", "checked");
        } else if(basicUser.getOccupation().equals("Government Sector")) {
            mp.put("governmentSector", "checked");
        } else if(basicUser.getOccupation().equals("Business")) {
            mp.put("businessOccupation", "checked");
        } else if(basicUser.getOccupation().equals("Professional")) {
            mp.put("professionalOccupation", "checked");
        } else if(basicUser.getOccupation().equals("Agriculturist")) {
            mp.put("agriculturistOccupation", "checked");
        } else if(basicUser.getOccupation().equals("Retired")) {
            mp.put("retiredOccupation", "checked");
        } else if(basicUser.getOccupation().equals("HouseWife")) {
            mp.put("houseWifeOccupation", "checked");
        } else if(basicUser.getOccupation().equals("Student")) {
            mp.put("studentOccupation", "checked");
        } else if(basicUser.getOccupation().equals("Forex Dealer")) {
            mp.put("forexDealerOccupation", "checked");
        } else if(basicUser.getOccupation().equals("Other")) {
            mp.put("othersOccupation", "checked");
        }


        // trading preference of use

//        ${futureSegment}
//        ${optionSegment}
//        ${cashSegment}
//        ${FAndOSegment}
//        ${currencySegment}

        String tradingPreference = "E-signed by : "+pan.getName()+" <br/>Date : "+fullDate+"<br/>Reason : Account Opening";
        log.info("--------------------  trading preference of user --- {}", user.getPhoneNumber() );
        if(basicUser.isNseCash()) mp.put("cashSegment" , tradingPreference);
        else mp.put("cashSegment" , "Not-Applicable<br/><br/>");
        if(basicUser.isNseFutureAndOption()) mp.put("FAndOSegment" , tradingPreference);
        else mp.put("FAndOSegment" , "Not-Applicable<br/><br/>");
        if(basicUser.isBseCash()) {
            mp.put("futureSegment" , tradingPreference);
            mp.put("optionSegment" , tradingPreference);
        }
        else {
            mp.put("futureSegment" , "Not-Applicable<br/><br/>");
            mp.put("optionSegment" , "Not-Applicable<br/><br/>");
        }
        if(basicUser.isBseCds()) mp.put("currencySegment" , tradingPreference);
        else mp.put("currencySegment" , "Not-Applicable<br/><br/>");

//      salary Annual data

        log.info("--------------------------------- below one lakh");
        mp.put("belowOneLakh", (basicUser.getAnnualSalary().equals("Below 1 Lac"))?"checked":"unchecked");
        mp.put("oneToFiveLakh", (basicUser.getAnnualSalary().equals("1-5 Lac"))?"checked":"unchecked");
        mp.put("fiveToTenLakh", (basicUser.getAnnualSalary().equals("5-10 Lac"))?"checked":"unchecked");
        mp.put("tenToTwentyFiveLakh", (basicUser.getAnnualSalary().equals("10-25 Lac"))?"checked":"unchecked");
        mp.put("twentyfiveLakhToOneCr", (basicUser.getAnnualSalary().equals("0.25-1 Cr"))?"checked":"unchecked");
        mp.put("greaterThenOneCr", (basicUser.getAnnualSalary().equals(">1 Cr"))?"checked":"unchecked");

//
        mp.put("dateOne", String.valueOf(date.charAt(0)));
        mp.put("dateTwo", String.valueOf(date.charAt(1)));
        mp.put("monthOne", String.valueOf(date.charAt(3)));
        mp.put("monthTwo", String.valueOf(date.charAt(4)));
        mp.put("yearOne", String.valueOf(date.charAt(6)));
        mp.put("yearTwo",String.valueOf(date.charAt(7)));
        mp.put("yearThree", String.valueOf(date.charAt(8)));
        mp.put("yearFour", String.valueOf(date.charAt(9)));

//        // aadhar mask number of user
        log.info("--------------------------- aadhar mask number");
        mp.put("uidNine", String.valueOf(aadharDataDao.getMaskAadharNumber().charAt(8)));
        mp.put("uidTen", String.valueOf(aadharDataDao.getMaskAadharNumber().charAt(9)));
        mp.put("uidEleven", String.valueOf(aadharDataDao.getMaskAadharNumber().charAt(10)));
        mp.put("uidTwelve", String.valueOf(aadharDataDao.getMaskAadharNumber().charAt(11)));

//        // pan card details of user
        log.info("-------------------------pan card");
        mp.put("panOne", String.valueOf(pan.getPanNumber().charAt(0)));;
        mp.put("panTwo", String.valueOf(pan.getPanNumber().charAt(1)));
        mp.put("panThree", String.valueOf(pan.getPanNumber().charAt(2)));
        mp.put("panFour", String.valueOf(pan.getPanNumber().charAt(3)));
        mp.put("panFive", String.valueOf(pan.getPanNumber().charAt(4)));
        mp.put("panSix", String.valueOf(pan.getPanNumber().charAt(5)));
        mp.put("panSeven", String.valueOf(pan.getPanNumber().charAt(6)));
        mp.put("panEight", String.valueOf(pan.getPanNumber().charAt(7)));
        mp.put("panNine", String.valueOf(pan.getPanNumber().charAt(8)));
        mp.put("panTen", String.valueOf(pan.getPanNumber().charAt(9)));

//       bank details of user
        log.info("-------------------------micr code");
        mp.put("micrOne", String.valueOf(bankData.getMicr().charAt(0)));
        mp.put("micrTwo", String.valueOf(bankData.getMicr().charAt(1)));
        mp.put("micrThree", String.valueOf(bankData.getMicr().charAt(2)));
        mp.put("micrFour", String.valueOf(bankData.getMicr().charAt(3)));
        mp.put("micrFive", String.valueOf(bankData.getMicr().charAt(4)));
        mp.put("micrSix", String.valueOf(bankData.getMicr().charAt(5)));
        mp.put("micrSeven", String.valueOf(bankData.getMicr().charAt(6)));
        mp.put("micrEight", String.valueOf(bankData.getMicr().charAt(7)));
        mp.put("micrNine", String.valueOf(bankData.getMicr().charAt(8)));

        log.info("-------------------------bank data");
        log.info("----------------- bank data {}", bankData);
        mp.put("ifscOne", String.valueOf(bankData.getIfsc().charAt(0)));
        mp.put("ifscTwo",String.valueOf(bankData.getIfsc().charAt(1)));
        mp.put("ifscThree",String.valueOf(bankData.getIfsc().charAt(2)));
        mp.put("ifscFour",String.valueOf(bankData.getIfsc().charAt(3)));
        mp.put("ifscFive",String.valueOf(bankData.getIfsc().charAt(4)));
        mp.put("ifscSix",String.valueOf(bankData.getIfsc().charAt(5)));
        mp.put("ifscSeven",String.valueOf(bankData.getIfsc().charAt(6)));
        mp.put("ifscEight",String.valueOf(bankData.getIfsc().charAt(7)));
        mp.put("ifscNine",String.valueOf(bankData.getIfsc().charAt(8)));
        mp.put("ifscTen",String.valueOf(bankData.getIfsc().charAt(9)));
        mp.put("ifscEleven",String.valueOf(bankData.getIfsc().charAt(10)));
        log.info("---------------- bank data ");
        String bankAc = bankData.getAccountNumber() ;


        log.info("--------------  account no of user {}", bankAc);
        String key = "accountNo" ;
        for(int i = 1  ; i <= 18 ; i++) {
            if(i < bankAc.length()) {
                mp.put(key+String.valueOf(i) , String.valueOf(bankAc.charAt(i-1)));
            } else {
                mp.put(key+String.valueOf(i) , "-");
            }
        }

//      client code of user

        log.info("------------------  client code of user {}" , user.getClientCode());
        for(int i = 0 ; i < 12 ; i++ ) {
            if(i<user.getClientCode().length()) {
                mp.put("ucc"+ (i + 1), String.valueOf(user.getClientCode().charAt(i)));
            }
            else {
                mp.put("ucc"+ (i+1) ,"");
            }
        }

        log.info("-------------------  user nominee one {}" , user.getNomineeOne());
//       nominee data of users
        if(!Objects.isNull(user.getNomineeOne())) {
            NomineeDao nomineeOne = nomineeDetailRepository.findById(user.getNomineeOne()).orElseThrow(()-> new BusinessException(ErrorCodes.USER_DETAILS_ARE_NOT_FOUND, HttpStatus.BAD_REQUEST));
            mp.put("nomineeOneName", nomineeOne.getName());
            mp.put("nomineeOneShare", nomineeOne.getSharePercentage());
            mp.put("nomineeOneAddress", getCompleteAddressOfAddressObject(nomineeOne.getAddressDao()));
            mp.put("nomineeOnePhone", nomineeOne.getContactNumber());
            mp.put("nomineeOnePinCode", nomineeOne.getAddressDao().getPincode());
            mp.put("nomineeOneMail", nomineeOne.getEmail());
        } else {
            mp.put("nomineeOneName", "-");
            mp.put("nomineeOneShare", "-");
            mp.put("nomineeOneAddress", "-");
            mp.put("nomineeOnePhone", "-");
            mp.put("nomineeOnePinCode", "-");
            mp.put("nomineeOneMail", "-");
        }
        log.info("-------------------  user nominee one {}" , user.getNomineeTwo());
        if(!Objects.isNull(user.getNomineeTwo())) {
            NomineeDao nomineeTwo = nomineeDetailRepository.findById(user.getNomineeOne()).orElseThrow(()-> new BusinessException(ErrorCodes.USER_DETAILS_ARE_NOT_FOUND, HttpStatus.BAD_REQUEST));
            mp.put("nomineeTwoName", nomineeTwo.getName());
            mp.put("nomineeTwoShare", nomineeTwo.getSharePercentage());
            mp.put("nomineeTwoAddress", getCompleteAddressOfAddressObject(nomineeTwo.getAddressDao()));
            mp.put("nomineeTwoPhone", nomineeTwo.getContactNumber());
            mp.put("nomineeTwoPinCode", nomineeTwo.getAddressDao().getPincode());
            mp.put("nomineeTwoMail", nomineeTwo.getEmail());
        } else {
            mp.put("nomineeTwoName", "-");
            mp.put("nomineeTwoShare", "-");
            mp.put("nomineeTwoAddress", "-");
            mp.put("nomineeTwoPhone", "-");
            mp.put("nomineeTwoPinCode", "-");
            mp.put("nomineeTwoMail", "-");
        }
        log.info("-------------------  user nominee one {}" , user.getNomineeThree());
        if(!Objects.isNull(user.getNomineeThree())) {
            NomineeDao nomineeThree = nomineeDetailRepository.findById(user.getNomineeOne()).orElseThrow(()-> new BusinessException(ErrorCodes.USER_DETAILS_ARE_NOT_FOUND, HttpStatus.BAD_REQUEST));
            mp.put("nomineeThreeName", nomineeThree.getName());
            mp.put("nomineeThreeShare", nomineeThree.getSharePercentage());
            mp.put("nomineeThreeAddress", getCompleteAddressOfAddressObject(nomineeThree.getAddressDao()));
            mp.put("nomineeThreePinCode", nomineeThree.getAddressDao().getPincode());
            mp.put("nomineeThreeMail", nomineeThree.getEmail());
            mp.put("nomineeThreePhone", nomineeThree.getContactNumber());
        } else {
            mp.put("nomineeThreeShare", "-");
            mp.put("nomineeThreeName", "-");
            mp.put("nomineeThreeAddress", "-");
            mp.put("nomineeThreePinCode", "-");
            mp.put("nomineeThreeMail", "-");
            mp.put("nomineeThreePhone", "-");
        }
        mp.put("futurenOptionSegmentBSE" , "Not implemented");
        mp.put("CurrencySegmentBSE" , "Not implemented");
        mp.put("CashSegmentBSE" , "Not implemented");
        mp.put("CashSegmentNSE" , "Not implemented");
        mp.put("FuturenOptionSegmentNSE" , "Not implemented");


        log.info("--------------------------  filling data of user in pdf {}", user.getPhoneNumber());
        byte[] pdfMainPage = ftlFiller(mp);
        log.info("-------- reached");
        log.info("------------{}", bankData);
        byte[] bankProofPdf = null;
        if(bankData.getBankValidationProof()!=null) {
            Base64.getDecoder().decode(bankData.getBankValidationProof());
        }
        PdfFile pdfFile =  mergePdfByte(pdfMainPage , pan.getPan_pdf(), bankProofPdf);
        pdfFile.setPanCard(pan.getPanNumber());
        pdfFile.setName(pan.getName());
        pdfFile.setEmail(user.getEmail());
        pdfFile.setMobile(user.getPhoneNumber());
        return pdfFile;
    }
    public byte[] ftlFiller(Map<String, String> model) {
        Template template = getTemplate();
        log.info("-------------------getting template name ");
        String html = "";
        try {
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            log.info("----------------------exception is there");
        }
        if (!html.isEmpty()) {
            return convertHtmlToPdf(html);
        } else {
            throw new BusinessException(ErrorCodes.PDF_FILE_NOT_AVAILIABLE , HttpStatus.BAD_REQUEST);
        }
    }

    public byte[] convertHtmlToPdf(String htmlString) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream, new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0));
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            pdfDocument.setTagged();
            ConverterProperties converterProperties = new ConverterProperties();
            HtmlConverter.convertToPdf(htmlString, pdfDocument, converterProperties);
            pdfDocument.close();
            byte[] pdfBytes = outputStream.toByteArray();
            return pdfBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Template getTemplate() {
        try {
            Template template = freeMarkerConfig.getTemplate("ryz_pdf.ftl");
            return template;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    private PdfFile mergePdfByte(byte[] mainPagePdf, byte[] panVerification, byte[] bankVerificationPdf) {
        try {
            com.itextpdf.text.pdf.PdfReader readerMainPdf = new com.itextpdf.text.pdf.PdfReader(mainPagePdf);
            com.itextpdf.text.pdf.PdfReader readerPan = new com.itextpdf.text.pdf.PdfReader(panVerification);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            com.itextpdf.text.pdf.PdfWriter writer = com.itextpdf.text.pdf.PdfWriter.getInstance(document, outputStream);
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            int pageCount3 = readerMainPdf.getNumberOfPages();
            for (int i = 0; i < pageCount3; i++) {
                document.newPage();
                PdfImportedPage page = writer.getImportedPage(readerMainPdf, i + 1);
                contentByte.addTemplate(page, 0, 0);
            }
            int pageCount1 = readerPan.getNumberOfPages();
            for (int i = 0; i < pageCount1; i++) {
                document.newPage();
                PdfImportedPage page = writer.getImportedPage(readerPan, i + 1);
                contentByte.addTemplate(page, 0, 0);
            }
            if (bankVerificationPdf != null && bankVerificationPdf.length > 0) {
                com.itextpdf.text.pdf.PdfReader readerBankDetails = new com.itextpdf.text.pdf.PdfReader(bankVerificationPdf);
                int pageCount2 = readerBankDetails.getNumberOfPages();
                for (int i = 0; i < pageCount2; i++) {
                    document.newPage();
                    PdfImportedPage page = writer.getImportedPage(readerBankDetails, i + 1);
                    contentByte.addTemplate(page, 0, 0);
                }
                readerBankDetails.close();
            }
            document.close();
            writer.close();
            readerMainPdf.close();
            readerMainPdf.close();
            PdfFile pdfFile = new PdfFile();
            pdfFile.setPdf(Base64.getEncoder().encodeToString(outputStream.toByteArray()));
            return pdfFile;
        } catch (Exception e) {
            throw new BusinessException(ErrorCodes.PDF_FILE_NOT_AVAILIABLE, HttpStatus.BAD_REQUEST);
        }
    }
    String getCompleteAddressOfAddressObject(AddressDao addressDao) {
        return addressDao.getHouseNumber()+" "+addressDao.getStreet()+" "+ addressDao.getDistrict()+" "+ addressDao.getState()+" "+addressDao.getCountry()+" "+addressDao.getPincode();
    }
}
