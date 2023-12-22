package com.back.office.controller;

import com.back.office.model.payload.dto.CommonResponse;
import com.back.office.service.PdfAndLeegalityService;
import com.back.office.service.rekyc.RekycEsignDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/")
public class PdfAndLeegalityController {
    private final PdfAndLeegalityService pdfAndLeegalityService;
    private final RekycEsignDocument rekycEsignDocument;

    public PdfAndLeegalityController(PdfAndLeegalityService pdfAndLeegalityService, RekycEsignDocument rekycEsignDocument) {
        this.pdfAndLeegalityService = pdfAndLeegalityService;
        this.rekycEsignDocument = rekycEsignDocument;
    }

    @GetMapping("ekyc/user/pdf/{phoneNumber}")
    public CommonResponse generatePdfOfUser(@PathVariable(name = "phoneNumber") String phoneNumber){
        return this.pdfAndLeegalityService.getPdfOfUser(phoneNumber);
    }

    @GetMapping("ekyc/user/leegality/notification/{phoneNumber}")
    public CommonResponse sendGenerateEsignPdfToEsign(@PathVariable(name = "phoneNumber") String phoneNumber) {
        return  this.pdfAndLeegalityService.sendPdfToEsignOfLeegality(phoneNumber);
    }


    @GetMapping("rekyc/esign/send/notification")
    public ResponseEntity<CommonResponse> sendGeneratedRekycEsignPdf(@RequestHeader("xuserid")UUID userid) {
        return ResponseEntity.ok(rekycEsignDocument.createRekycPdf(userid));
    }
}
