package com.back.office.model.dao;

import com.back.office.enums.KycStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_details_main")
public class UserDetailMainDao extends BaseDao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
    @Column(name = "id" , columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "client_code")
    private String clientCode;
    @Column(name = "phone_number")
    private String phoneNumber ;
    @Column(name = "email")
    private String email;
    @Column(name = "bank_details")
    private UUID bankDetailId;
    @Column(name = "adhar_id")
    private UUID adharId ;
    @Column(name = "basic_user_information_id")
    private UUID basicUserInformationId;
    @Column(name= "pan_card_id")
    private UUID panCardId ;
    @Column(name = "nominee_one")
    private UUID nomineeOne ;
    @Column(name = "nominee_two")
    private UUID nomineeTwo ;
    @Column(name = "nominee_three")
    private UUID nomineeThree ;
    @Column(name = "code_verifier")
    private String codeVerifier ;
    @Column(name = "kyc_status")
    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus;
    @Column(name = "image_and_signature_id")
    private UUID imageAndSignatureId ;
    @Column(name = "document_id")
    private String documentId ;
    @Column(name = "audit_trail")
    @Lob
    private String auditTrail ;
    @Column(name = "e_sign_pdf")
    @Lob
    private String eSignedPdf;
    @Column(name = "rejected_docs")
    private String rejecetdDocsCommaSeprated;
}
