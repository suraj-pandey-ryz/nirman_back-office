package com.back.office.enums;

public enum NotificationType {
        SMS("SMS"),

        EMAIL("EMAIL");

        private String value;

        public String getValue(){
            return this.value = value;
        };

        private NotificationType(String value) {
            this.value = value;
        }
}
