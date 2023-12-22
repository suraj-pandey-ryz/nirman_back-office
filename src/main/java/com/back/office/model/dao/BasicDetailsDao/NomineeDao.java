package com.back.office.model.dao.BasicDetailsDao;

import com.back.office.model.dao.AddressDao;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "nominee")
public class NomineeDao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
    @Column(name = "id" , columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "name")
    private String name ;
    @Column(name = "date_of_birth")
    private String dateOfBirth ;
    @Column(name = "contact_number")
    private String contactNumber ;
    @Column(name = "email")
    private String email ;
    @Column(name = "relation")
    private String relation ;
    @Column(name = "proof_of_address")
    @Lob
    private String proofOfAddress ;
    @Column(name = "type_of_document")
    private String typeOfDocument;
    @Column(name = "poi_reference_number")
    private String poiReferenceNumber;
    @Column(name = "share_percentage")
    private String sharePercentage ;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "guardian_fk_id")
    private GuardianDao guardianDao;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_fk_id")
    private AddressDao addressDao;
}
