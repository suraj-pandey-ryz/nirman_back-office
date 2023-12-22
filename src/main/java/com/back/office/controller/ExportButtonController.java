package com.back.office.controller;

import com.back.office.model.payload.ExportDocuments;
import com.back.office.service.ExportButtonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class ExportButtonController {
    private final ExportButtonService exportButtonService;
    public ExportButtonController(ExportButtonService exportButtonService) {
        this.exportButtonService = exportButtonService;
    }
    @GetMapping("export/button/{phoneNumber}")
    public ResponseEntity<ExportDocuments> getExportButtonController(@PathVariable(name = "phoneNumber") String phoneNumber) {
        return ResponseEntity.ok(exportButtonService.getExportButtonDocuments(phoneNumber));
    }
}
