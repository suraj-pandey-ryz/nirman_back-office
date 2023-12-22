package com.back.office.model.dao.BasicDetailsDao;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "basic_user_detail")
public class BasicUserInfoDao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
    @Column(name = "id" , columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "martial_status")
    private String maritalStatus ;
    @Column(name = "spouse_name")
    private String spouseName ;
    @Column(name = "mother_name")
    private String motherName ;
    @Column(name= "father_name")
    private String fatherName ;
    @Column(name = "education")
    private String education;
    @Column(name = "trading_experience")
    private String tradingExperience ;
    @Column(name = "occupation")
    private String occupation ;
    @Column(name = "nominee_check")
    private String nomineeCheck ;
    @Column(name = "tax_residance")
    private String taxResidence ;
    @Column(name = "net_worth")
    private String netWorth ;
    @Column(name = "annual_salary")
    private String annualSalary;
    @Column(name = "politically_exposed")
    private String politicallyExposed ;
    @Column(name = "nse_cash")
    private boolean nseCash ;
    @Column(name = "nse_future_and_option")
    private boolean nseFutureAndOption ;
    @Column(name = "nse_cds")
    private boolean bseCds ;
    @Column(name = "nse_mcx")
    private boolean bseCash ;
    @Column(name = "bse_future_And_option")
    private boolean bseFutureAndOption;
    @Column(name = "nationality")
    private String nationality ;
    @Column(name = "brokerage_amount")
    private String brokerageAmount;
    @Column(name = "action_taken_by_sebi")
    private String actionTakenBySebiOrAnyOtherAuthority ;
    @Column(name = "action_taken_by_sebi_description")
    private String actionTakenBySebiOrAnyOtherAuthorityDescription ;
    @Lob
    @Column(name = "financial_proof_document")
    private String financialProofDocument ;
}
