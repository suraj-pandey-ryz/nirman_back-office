package com.back.office.enums;

public enum RekycStatus {
    CLIENT_DATA("CLIENT_DATA"),
    VERIFICATION("VERIFICATION"),
    REQUESTED("REQUESTED"),
    REJECTED("REJECTED"),
    ESIGNED("ESIGNED"),
    ESIGN_REQUESTED("ESIGN_REQUESTED");
    private String value;

    public String getValue(){
        return this.value = value;
    };

    private RekycStatus(String value) {
        this.value = value;
    }
}
