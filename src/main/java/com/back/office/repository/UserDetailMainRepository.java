package com.back.office.repository;

import com.back.office.enums.KycStatus;
import com.back.office.model.dao.UserDetailMainDao;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailMainRepository extends JpaRepository<UserDetailMainDao, UUID> {
    Optional<UserDetailMainDao> findByPhoneNumber(String phoneNumber);

    //    List<UserDetailMainDao> findAllByKycStatus(KycStatus kycStatus, PageRequest pageRequest);
    Page<UserDetailMainDao> findByPhoneNumberContainingOrEmailContainingOrClientCodeContaining(String phone, String email, String clientCode, PageRequest pageRequest);

    @Query("SELECT e FROM UserDetailMainDao e WHERE (e.phoneNumber LIKE %:text% OR e.email LIKE %:text% OR e.clientCode LIKE %:text%) AND e.kycStatus IN :kycStatusValues")
    Page<UserDetailMainDao> searchUserDetailsInDBAndFilterData(@Param("text") String text, @Param("kycStatusValues") List<KycStatus> kycStatusValues, PageRequest pageRequest);
}