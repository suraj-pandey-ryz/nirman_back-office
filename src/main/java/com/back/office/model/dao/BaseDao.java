package com.back.office.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass
public class BaseDao {
    @Column(name = "created_at")
    protected long createdAt ;
    @Column(name = "updated_at")
    protected long updatedAt ;
    @PrePersist
    void onCreate() {
        createdAt = Instant.now().toEpochMilli();
        updatedAt = Instant.now().toEpochMilli();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now().toEpochMilli();
    }
}
