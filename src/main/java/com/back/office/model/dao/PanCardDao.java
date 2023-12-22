package com.back.office.model.dao;
import com.back.office.enums.Gender;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "pan_card")
public class PanCardDao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
    @Column(name = "id" , columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "pan_number")
    private String panNumber ;
    @Column(name = "name")
    private String name ;
    @Column(name = "gender")
    private Gender gender ;
    @Column(name = "date_of_birth")
    private String dob ;
    @Column(name = "pan_card_pdf")
    @Lob
    private byte[] pan_pdf ;
}
