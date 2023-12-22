package com.back.office.model.dao.rekyc;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "rekyc_user_additional_details")
@Data
public class RekycAdditionalDetailsDao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "annual_salary")
    private String incomeRange;
    @Column(name = "is_new_bank_default")
    private boolean isNewBankDefault;
    @Column(name = "user_image")
    @Lob
    private String image;
    @Column(name = "user_signature")
    @Lob
    private String signature;
    @Column(name = "bank_proof")
    @Lob
    private String bankProof;
    @Lob
    @Column(name = "financial_proof")
    private String financialProof;
    @Column(name = "is_bank_type_image")
    private boolean isBankProofImage;
    @Column(name = "is_financial_type_image")
    private boolean isFinancialProofImage ;
}
