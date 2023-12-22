package com.back.office.model.dao;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "aadhar_data")
public class AadharDataDao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
    @Column(name = "id" , columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "mask_number")
    private String maskAadharNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birth")
    private String dob;
    @Column(name = "time_stamp")
    private String timeStampGeneratedTimeActual;
    @Column(name = "time_to_live")
    private String timeToLiveActual ;
    @Column(name = "gender")
    private String gender;
    @Column(name = "generated_time")
    private String downloadedTimeInternal ;
    @Column(name = "care_of")
    private String careOf ;
    @Column(name = "aadhar_xml")
    @Lob
    private String aadharXml ;
    @Column(name = "photoBase64")
    @Lob
    private String aadharProfilePhotoBase64  ;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private AddressDao aadharAddress ;
}
