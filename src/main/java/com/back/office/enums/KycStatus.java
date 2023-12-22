package com.back.office.enums;

public enum KycStatus {
    LOGIN("LOGIN"),
    DIGILOCKER("DIGILOCKER"),
    PERSONAL_INFO("PERSONAL_INFO"),
    BANK_DETAILS("BANK_DETAILS"),
    SEGMENT("SEGMANT"),
    VERIFICATION("VERIFICATION"),
    UPLOAD_PHOTO("UPLOAD_PHOTO"),
    BANK_PROOF_UPLOAD("BANK_PROOF_UPLOAD"),
    COMPLETED("COMPLETED"),
    DOWNLOAD_PDF("DOWNLOAD_PDF"),
    PAN_PROOF_REQUIRED("PAN_PROOF_REQUIRED"),
    E_SIGN("E_SIGN"),
    REJECTED("REJECTED"),
    RE_UPLOADED_DOCS("RE_UPLOADED_DOCS"),
    USER_PROCESS_COMPLETED("USER_PROCESS_COMPLETED"),
    THANK_YOU("THANK_YOU");
    private String value ;
    public String getValue() {
        return value;
    }
    private KycStatus(String value){
        this.value = value ;
    }
}
