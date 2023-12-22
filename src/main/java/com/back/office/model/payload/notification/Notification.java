package com.back.office.model.payload.notification;

import com.back.office.enums.RejectDocument;
import lombok.*;


import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Notification {
        private String to;
        private String subject;
        private String message;
        private String templateType;
        private Map<String,String> model;
        public Notification(String message) {
                this.message = message;
        }
}
