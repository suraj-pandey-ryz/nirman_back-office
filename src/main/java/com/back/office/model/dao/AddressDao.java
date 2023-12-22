package com.back.office.model.dao;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "address")
public class AddressDao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
    @Column(name = "id" , columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "house_number")
    private String houseNumber ;
    @Column(name = "location")
    private String location ;
    @Column(name = "street")
    private String street ;
    @Column(name = "voting_center")
    private String votingCenter ;
    @Column(name = "district")
    private String district ;
    @Column(name = "pincode")
    private String pincode ;
    @Column(name = "state")
    private String state ;
    @Column(name = "country")
    private String country ;
}
