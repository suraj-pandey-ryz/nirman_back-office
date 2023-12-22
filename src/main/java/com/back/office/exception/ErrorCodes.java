package com.back.office.exception;

public enum ErrorCodes {
    LOGIN_FAIL("BAO_001"),
    USER_NOT_PERSENT("BAO_002"),
    USER_INFORMATIONS_ARE_INCOMPLETE("BAO_004"),
    ERROR_IN_REJECTED_DOCUMENT("BAO_003"),
    USER_NOT_REGISTERED("BAO_010"),
    PDF_FILE_NOT_AVAILIABLE("BAO_011"),
    E_SIGN_NOT_WORKED("BAO-012"),
    USER_PERSONAL_DETAILS_NOT_FOUND("BAO_13"),
    REKYC_USER_NOT_FOUND("BAO_14"),
    BACK_OFFICE_DATA_CANNOT_BE_READ("BAO_15"),
    NOTIFICATION_SERVICE_IS_NOT_AVAILABLE("BAO_15"),
    NOTIFICATION_SERVICE_CANNOT_BE_SERLIZED("BAO_16"),
    USER_EMAIL_NOT_FOUND_PARTIALLY_UPDATED_USER("BAO_17"),
    USER_DETAILS_ARE_NOT_FOUND("BAO_18"),
    BACKOFFICE_NOT_AVAILABLE("BAO_19");
    private String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    private ErrorCodes(String value) {
        this.value = value;
    }
}
