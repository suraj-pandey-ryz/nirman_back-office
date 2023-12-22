package com.back.office.model.dao.BasicDetailsDao;

import com.back.office.enums.AccountType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "bank_details")
public class BankDetailsDao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
    @Column(name = "id" , columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "bank_register_name")
    private String bankRegisteredName ;
    @Column(name = "ifsc_code")
    private String ifsc ;
    @Column(name = "account_number")
    private String accountNumber ;
    @Column(name = "payout_id")
    private String payoutId ;
    @Column(name = "penny_drop_reason")
    private String pennyDropReason ;
    @Column(name = "micr")
    private String micr ;
    @Column(name = "bank_name")
    private String bankName ;
    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(name = "branch")
    private String branch ;
    @Column(name = "address")
    private String address ;
    @Column(name = "status")
    private String status ;
    @Column(name = "validation_proof")
    @Lob
    private String bankValidationProof ;
}
