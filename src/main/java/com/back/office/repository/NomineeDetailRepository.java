package com.back.office.repository;

import com.back.office.model.dao.BasicDetailsDao.NomineeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NomineeDetailRepository extends JpaRepository<NomineeDao, UUID> {
    List<NomineeDao> findByName(String name);
}
