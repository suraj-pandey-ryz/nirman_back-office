package com.back.office.repository;

import com.back.office.model.dao.BasicDetailsDao.BasicUserInfoDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BasicUserDetailsRepository extends JpaRepository<BasicUserInfoDao, UUID> {
}
