package com.back.office.model.dao.rekyc;

import com.back.office.enums.RekycLockStatus;
import com.back.office.enums.RekycStatus;
import com.back.office.model.dao.AadharDataDao;
import com.back.office.model.dao.BaseDao;
import com.back.office.model.dao.BasicDetailsDao.BankDetailsDao;
import com.back.office.model.dao.BasicDetailsDao.NomineeDao;
import com.back.office.model.dao.PanCardDao;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "rekyc_users")
public class RekycUserDetailsDao extends BaseDao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "client_code")
    private String clientCode ;
    private String mobile;
    private String email ;
    private String name;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_detail_fk")
    private BankDetailsDao bankDetailsDao;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "aadhar_fk_id")
    private AadharDataDao aadharDataDao;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pan_card_fk")
    private PanCardDao panCardDao;
    @Column(name = "lock_status")
    @Enumerated(EnumType.STRING)
    private RekycLockStatus lockStatus;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name= "nominee_fk_id")
    private NomineeDao nomineeDao;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rekyc_fk_id")
    private RekycAdditionalDetailsDao rekycAdditionalDetailsDao;
    @Column(name = "rekyc_status")
    @Enumerated(EnumType.STRING)
    private RekycStatus rekycStatus;
    @Column(name = "rejected_docs")
    private String rejecetdDocsCommaSeprated;
//    segment , addhar , pan, address , status , lockedStatus
}
