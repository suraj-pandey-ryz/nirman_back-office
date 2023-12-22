package com.back.office.enums;

public enum TemplateType {
    NIRMAN_KYC_REJECTED_EMAIL("NIRMAN_KYC_REJECTED_EMAIL"),
    NIRMAN_REKYC_REJECTION_EMAIL("NIRMAN_REKYC_REJECTION_EMAIL"),
    NIRMAN_KYC_ESIGN_NOTIFICATION("NIRMAN_KYC_ESIGN_NOTIFICATION"),
    NIRMAN_REKYC_ESIGN_NOTIFICATION("NIRMAN_REKYC_ESIGN_NOTIFICATION");

    private String value ;
    public String getValue() {
        return value;
    }
    TemplateType(String value) {
        this.value = value;
    }
}
