package com.back.office.model.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExportDocuments {
    private String auditTrailPdf;
    private String eSignedPdf;
    private String aadharXml;
}
