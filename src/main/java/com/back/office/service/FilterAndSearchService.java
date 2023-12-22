package com.back.office.service;

import com.back.office.enums.KycStatus;
import com.back.office.model.dao.UserDetailMainDao;
import com.back.office.model.payload.filter.FilterAndSearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FilterAndSearchService {
    FilterAndSearchResponse filterAndSearchDetailsOfClients(String search, KycStatus kycStatus, Integer pageNumber);
}
