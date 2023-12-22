package com.back.office.enums;

public enum RekycLockStatus {
    LOCKED("LOCKED"),
    UNLOCKED("UNLOCKED");
    private String value ;
    public String getValue() {
        return value;
    }
    RekycLockStatus(String value) {
        this.value = value;
    }
}
