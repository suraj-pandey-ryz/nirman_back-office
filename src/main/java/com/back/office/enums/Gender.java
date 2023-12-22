package com.back.office.enums;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    TRANSGENDER("TRANSGENDER"),
    OTHER("OTHER");
    private String value;
    public String getValue(){
        return this.value = value;
    };
    private Gender(String value) {
        this.value = value;
    }
    public static Gender fromValue(String value) {
        try {
            return Gender.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return OTHER;
        }
    }
}
