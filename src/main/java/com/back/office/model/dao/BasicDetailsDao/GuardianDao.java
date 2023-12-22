package com.back.office.model.dao.BasicDetailsDao;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Table(name = "guardian_details")
@Entity
public class GuardianDao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
    @Column(name = "id" , columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    @Column(name = "relation_with_nominee")
    private String relationWithNominee;
    @Column(name = "id_proof")
    private String idProof;
    @Column(name = "id_proof_no")
    private String idProofNo;
    @Column(name = "address")
    private String address ;
}
