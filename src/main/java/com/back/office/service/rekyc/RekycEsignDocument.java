package com.back.office.service.rekyc;

import com.back.office.enums.NotificationType;
import com.back.office.enums.TemplateType;
import com.back.office.exception.BusinessException;
import com.back.office.exception.ErrorCodes;
import com.back.office.model.dao.rekyc.RekycUserDetailsDao;
import com.back.office.model.payload.PdfFile;
import com.back.office.model.payload.dto.CommonResponse;
import com.back.office.notifications.NotificationService;
import com.back.office.repository.RekycUserRepository;
import com.back.office.service.LeegalityService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
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
public class RekycEsignDocument {
    private final Configuration freeMarkerConfig;
    private final RekycUserRepository rekycUserRepository;
    private final RekycBackOfficeDetailsService rekycBackOfficeDetailsService;
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
    private final LeegalityService leegalityService;

    public RekycEsignDocument(Configuration freeMarkerConfig, RekycUserRepository rekycUserRepository, RekycBackOfficeDetailsService rekycBackOfficeDetailsService, ObjectMapper objectMapper, NotificationService notificationService, LeegalityService leegalityService) {
        this.freeMarkerConfig = freeMarkerConfig;
        this.rekycUserRepository = rekycUserRepository;
        this.rekycBackOfficeDetailsService = rekycBackOfficeDetailsService;
        this.objectMapper = objectMapper;
        this.notificationService = notificationService;
        this.leegalityService = leegalityService;
    }
    public CommonResponse createRekycPdf(UUID xuserid) {
        RekycUserDetailsDao rekycUserDetailsDao = rekycUserRepository.findById(xuserid).orElseThrow(()-> new BusinessException(ErrorCodes.REKYC_USER_NOT_FOUND, HttpStatus.BAD_REQUEST));
        String response = rekycBackOfficeDetailsService.backOfficeDetailsOfUser(rekycUserDetailsDao.getClientCode());
        Map<String, String> model = new HashMap<>();
        // only new reactive
        // cash
        model.put("BSE", "unchecked");
        // BSE F&O
        model.put("BFO", "unchecked");
        // BSE Comodity
        model.put("BCD", "unchecked");
        model.put("NSE", "unchecked");
        model.put("NSEFO", "unchecked");
        log.info("--------------------------------1");
        try {
            JsonNode rootNode1 = objectMapper.readTree(response);
            String jsonResponse = rootNode1.get(0).toString();
            Map<String, String> dataMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, String>>() {});
            // segment list
            List<String> rejected = Arrays.asList(dataMap.get("activeexchange").split(","));
            // setting those field checked which will come from backoffice segment selected list
            for(String x : rejected) {
                if(model.containsKey(x)) {
                    model.put(x, "checked");
                }
            }
            log.info("--------------------------------1");
            model.put("panNumber", dataMap.get("panno"));
            model.put("dpId", dataMap.get("dpid"));
            model.put("firstSoleHolder", dataMap.get("name"));
            model.put("firstSoleHolderSign", "NOT_AVAILABLE");
            if(!Objects.isNull(rekycUserDetailsDao.getAadharDataDao())) {
                model.put("permanentAddCheckBox", "unchecked");
                model.put("correspondenceAddCheckBox", "unchecked");
                model.put("newAddressLine1", rekycUserDetailsDao.getAadharDataDao().getAadharAddress().getHouseNumber()+" "+ rekycUserDetailsDao.getAadharDataDao().getAadharAddress().getStreet());
                model.put("newAddressLine2", rekycUserDetailsDao.getAadharDataDao().getAadharAddress().getLocation() + " " +rekycUserDetailsDao.getAadharDataDao().getAadharAddress().getDistrict());
                model.put("newState", rekycUserDetailsDao.getAadharDataDao().getAadharAddress().getState());
                model.put("newPinCode", rekycUserDetailsDao.getAadharDataDao().getAadharAddress().getPincode());
                model.put("newCountry", rekycUserDetailsDao.getAadharDataDao().getAadharAddress().getCountry());
                // old address details
                model.put("oldAddressLine1", dataMap.get("add1"));
                model.put("oldAddressLine2", dataMap.get("add2"));
                model.put("oldState", dataMap.get("add3"));
                model.put("oldPinCode", dataMap.get("pincode"));
                model.put("oldCountry", dataMap.get("add4"));
            } else {
                model.put("newAddressLine1", "");
                model.put("newAddressLine2", "");
                model.put("newState", "");
                model.put("newPinCode", "");
                model.put("newCountry", "");
                model.put("oldAddressLine1", "");
                model.put("oldAddressLine2", "");
                model.put("oldState", "");
                model.put("oldPinCode", "");
                model.put("oldCountry", "");
            }
            log.info("--------------------------------1");
            // bank details addition
            model.put("isNewBankDefault", "unchecked");
            model.put("newSBCheckBox", "unchecked");
            model.put("newCACheckBox" , "unchecked");
            if(rekycUserDetailsDao.getRekycAdditionalDetailsDao().isNewBankDefault()) {
                model.put("isNewBankDefault", "checked");
                // old bank details
                model.put("oldBankName", dataMap.get("bankname"));
                model.put("oldAccountNo", dataMap.get("900510110000557"));
                model.put("oldIfscCode",dataMap.get("ifsccode"));
                model.put("oldMicr", dataMap.get("micrcode"));
                model.put("oldBranchName", "NA");
                model.put("newBankName", rekycUserDetailsDao.getBankDetailsDao().getBankName());
                model.put("newAccountNo", rekycUserDetailsDao.getBankDetailsDao().getAccountNumber());
                model.put("newIfscCode",rekycUserDetailsDao.getBankDetailsDao().getIfsc());
                model.put("newMicr", rekycUserDetailsDao.getBankDetailsDao().getMicr());
                model.put("newBranchName", rekycUserDetailsDao.getBankDetailsDao().getBranch());
                switch (rekycUserDetailsDao.getBankDetailsDao().getAccountType()) {
                    case SAVING:
                        model.put("newSBCheckBox", "checked");
                        break;
                    case CURRENT:
                        model.put("newCACheckBox", "checked");
                        break;
                }
            } else {
                model.put("oldBankName", "");
                model.put("oldAccountNo", "");
                model.put("oldIfscCode","");
                model.put("oldMicr", "");
                model.put("oldBranchName", "");
                model.put("newBankName", "");
                model.put("newAccountNo", "");
                model.put("newIfscCode","");
                model.put("newMicr", "");
                model.put("newBranchName", "");
            }
            log.info("--------------------------------1");
            // client reactive or not
            if(dataMap.get("accountstatus").equals("Disabled")){ model.put("reactivationCheckBox", "unchecked");}
                else { model.put("reactivationCheckBox", "unchecked"); }
            // client mobile and email data
            model.put("oldMobile", dataMap.get("mobileno"));
            model.put("oldEmail", dataMap.get("emailno"));
        } catch (Exception e) {
            // issue in some data reading object check correctly in every datamap
            throw new BusinessException(ErrorCodes.BACK_OFFICE_DATA_CANNOT_BE_READ, HttpStatus.BAD_REQUEST);
        }
        log.info("--------------------------------1");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        model.put("date", date);
        model.put("dp", "checked"); // depend
        // backoffice se dpid
        model.put("clientId", "NOT_AVAILABLE"); //dpaccountno client id
        model.put("uccId", "NOT_AVAILABLE"); // missing
        // mobile and email details
        model.put("changeEmailAndMobileCheckBox", "unchecked");

        if(!Objects.isNull(rekycUserDetailsDao.getMobile())) {
            model.put("newMobile", rekycUserDetailsDao.getMobile());
        } else {
            model.put("newMobile", "");
        } log.info("-----------------afdaf---------------1");
        if(!Objects.isNull(rekycUserDetailsDao.getEmail())) {
            model.put("newEmail", rekycUserDetailsDao.getEmail());
        } else {
            model.put("newEmail", "");
        } log.info("--------------------------------1");
        // basic details
        // income details not avaliable clearly in dpid like number not string
        model.put("oldIncomeDetails", "NA");
        model.put("newIncomeDetails", "NA");
        model.put("updationInAnnualCheckBox", "NA");
        model.put("updationInNetworthCheckBox", "NA");
        // networth is not available
        model.put("oldNetWorth", "NOT_AVAILIABLE");
        model.put("newNetWorth", "NOT_AVAILIABLE");

        // not have enough resources to be done
        model.put("dpSealAndSignature", "NOT_AVAILIABLE");
        log.info("----------------1lastadfaf----------------1");
        // client specific data not have enought look like shit to me
        model.put("receivedBy", "NOT_AVAILIABLE");
        model.put("verifiedBy", "NOT_AVAILIABLE");
        model.put("enteredBy", "checked");
        log.info("-------------------1last--------1last-----1");
        model.put("userImage" , "" );
        model.put("userSignature" , "" );
        model.put("bankProof" , "" );
        model.put("financialProof" , "" );
//        if(!Objects.isNull(rekycUserDetailsDao.getRekycAdditionalDetailsDao().getImage())) {
//            model.put("userImage" , rekycUserDetailsDao.getRekycAdditionalDetailsDao().getImage() );
//        } else {
//            model.put("userImage" , "" );
//        }
//        model.put("financialProof" , "" );
//        if(!Objects.isNull(rekycUserDetailsDao.getRekycAdditionalDetailsDao().getSignature())) {
//            model.put("userSignature" , rekycUserDetailsDao.getRekycAdditionalDetailsDao().getSignature() );
//        } else {
//            model.put("userSignature" , "" );
//        } log.info("------------------------1last-------1last-1");
//        if(!Objects.isNull(rekycUserDetailsDao.getRekycAdditionalDetailsDao().getBankProof())) {
//            model.put("bankProof" , rekycUserDetailsDao.getRekycAdditionalDetailsDao().getBankProof() );
//        } else {
//            model.put("bankProof" , "" );
//        } log.info("--------------------------------1last1");
//        if(!Objects.isNull(rekycUserDetailsDao.getRekycAdditionalDetailsDao().getFinancialProof())) {
//            model.put("financialProof" , rekycUserDetailsDao.getRekycAdditionalDetailsDao().getFinancialProof() );
//        } else {
//            model.put("financialProof" , "" );
//        } log.info("--------------------------------1last");
        PdfFile pdfFile = new PdfFile();
        log.info("--------------------------------1last");
        byte[] fm = ftlFiller(model);
        pdfFile.setPdf(Base64.getEncoder().encodeToString(ftlFiller(model)));
        pdfFile.setName(model.get("firstSoleHolder"));
        pdfFile.setEmail(model.get("oldEmail"));
        pdfFile.setMobile(model.get("oldMobile"));
        pdfFile.setPanCard(model.get("panNumber"));
        return generateEsignLinkAndSendEsignLinkToEmail(pdfFile);
    }
    public byte[] ftlFiller(Map<String, String> model) {
        Template template = getTemplate();
        String html = "";
        try {
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            System.out.println("----------------------exception is there");
        }
        if (!html.isEmpty()) {
            return convertHtmlToPdf(html);
        } else {
            throw new BusinessException(ErrorCodes.PDF_FILE_NOT_AVAILIABLE , HttpStatus.BAD_REQUEST);
        }
    }

    public byte[] convertHtmlToPdf(String htmlString) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            log.info("-------------------------- working1");
            PdfWriter writer = new PdfWriter(outputStream, new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0));
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            pdfDocument.setTagged();
            log.info("-------------------------- working1");
            ConverterProperties converterProperties = new ConverterProperties();
            HtmlConverter.convertToPdf(htmlString, pdfDocument, converterProperties);
            pdfDocument.close();
            log.info("-------------------------- working1");
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private Template getTemplate() {
        try {
            return freeMarkerConfig.getTemplate("rekyc_pdf.ftl");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private CommonResponse generateEsignLinkAndSendEsignLinkToEmail(PdfFile pdfFile) {
        String signUrl = leegalityService.esignPdfusingLegality(pdfFile);
        Map<String , String> mp = new HashMap<>();
        mp.put("name" , pdfFile.getName());
        mp.put("esignurl", pdfFile.getEmail());
        notificationService.sendNotification(pdfFile.getEmail(), TemplateType.NIRMAN_KYC_ESIGN_NOTIFICATION, NotificationType.EMAIL, mp);
        return new CommonResponse("esign send success");
    }
}
