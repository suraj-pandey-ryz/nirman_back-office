package com.back.office.model.dao;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "image_signature")
@Data
public class ImageSignatureDao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "UUID_gen_strategy_class", value = "org.hibernate.id.UUID.CustomVersionOneStrategy") })
    @Column(name = "id" , columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "image")
    @Lob
    private String image ;
    @Column(name = "signature")
    @Lob
    private String signature ;
    @Column(name = "longitude")
    private String longitude ;
    @Column(name = "latitude")
    private String latitude;
    @Column(name="address")
    private String address ;
    @Column(name = "created_at")
    private Long createdAt = Instant.now().toEpochMilli();
}
