package com.back.office.service;

import com.back.office.exception.BusinessException;
import com.back.office.exception.ErrorCodes;
import com.back.office.model.dao.AadharDataDao;
import com.back.office.model.dao.UserDetailMainDao;
import com.back.office.model.payload.ExportDocuments;
import com.back.office.repository.AadharDataRepository;
import com.back.office.repository.UserDetailMainRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ExportButtonService {
    private final UserDetailMainRepository userDetailMainRepository;
    private final AadharDataRepository aadharDataRepository;

    public ExportButtonService(UserDetailMainRepository userDetailMainRepository, AadharDataRepository aadharDataRepository) {
        this.userDetailMainRepository = userDetailMainRepository;
        this.aadharDataRepository = aadharDataRepository;
    }

    public ExportDocuments getExportButtonDocuments(String phoneNumber) {
        Optional<UserDetailMainDao> users = userDetailMainRepository.findByPhoneNumber(phoneNumber);
        if(users.isEmpty()) {
            throw new BusinessException(ErrorCodes.USER_NOT_PERSENT, HttpStatus.BAD_REQUEST);
        }
        if(Objects.isNull(users.get().getAdharId())) throw new BusinessException(ErrorCodes.USER_INFORMATIONS_ARE_INCOMPLETE, HttpStatus.BAD_REQUEST);
        AadharDataDao aadharData = aadharDataRepository.findById(users.get().getAdharId()).orElseThrow(()->new BusinessException(ErrorCodes.USER_NOT_REGISTERED, HttpStatus.BAD_REQUEST));
        return ExportDocuments.builder().auditTrailPdf(users.get().getAuditTrail()).eSignedPdf(users.get().getESignedPdf()).aadharXml(aadharData.getAadharXml()).build();
    }
}
