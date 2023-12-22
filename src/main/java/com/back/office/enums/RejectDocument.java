package com.back.office.enums;

public enum RejectDocument {
    PAN_CARD("PAN_CARD"),
    BANK_PROOF("BANK_PROOF"),
    FINANCIAL_PROOF("FINANCIAL_PROOF"),
    SIGNATURE("SIGNATURE") ,
    IMAGE("IMAGE"),
    NOMINEE_ONE("NOMINEE_ONE"),
    NOMINEE_TWO("NOMINEE_TWO"),
    NOMINEE_THREE("NOMINEE_THREE");
    private String value ;
    public String getValue() {
        return this.value =value;
    }
    private RejectDocument(String value){
        this.value = value ;
    }
}
