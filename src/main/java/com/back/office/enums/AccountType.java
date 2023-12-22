package com.back.office.enums;

public enum AccountType {
    SAVING("SAVING"),
    CURRENT("CURRENT");
    private String value;
    public String getValue(){
        return this.value = value;
    };

    private AccountType(String value) {
        this.value = value ;
    }
}
