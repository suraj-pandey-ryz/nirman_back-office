package com.back.office.repository;

import com.back.office.enums.KycStatus;
import com.back.office.enums.RekycStatus;
import com.back.office.model.dao.UserDetailMainDao;
import com.back.office.model.dao.rekyc.RekycUserDetailsDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RekycUserRepository extends JpaRepository<RekycUserDetailsDao, UUID> {
    Optional<RekycUserDetailsDao> findByClientCode(String clientCode);

    @Query("SELECT e FROM RekycUserDetailsDao e WHERE (e.clientCode LIKE %:text% OR e.mobile LIKE %:text% OR e.name LIKE %:text%) AND e.rekycStatus = :rekycStatus")
    Page<RekycUserDetailsDao> searchUserDetailsInDBAndFilterData(@Param("text") String text, @Param("rekycStatus") RekycStatus rekycStatus, PageRequest pageRequest);

}
