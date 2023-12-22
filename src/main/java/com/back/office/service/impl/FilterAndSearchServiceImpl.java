package com.back.office.service.impl;

import com.back.office.enums.KycStatus;
import com.back.office.model.dao.PanCardDao;
import com.back.office.model.dao.UserDetailMainDao;
import com.back.office.model.payload.UserAdminPageContent;
import com.back.office.model.payload.filter.FilterAndSearchResponse;
import com.back.office.model.payload.filter.PageDetail;
import com.back.office.repository.PanCardRepository;
import com.back.office.repository.UserDetailMainRepository;
import com.back.office.service.FilterAndSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class FilterAndSearchServiceImpl implements FilterAndSearchService {
    private final UserDetailMainRepository userDetailMainRepository ;
    private final PanCardRepository panCardRepository;
    private final int pageSize = 10 ;

    public FilterAndSearchServiceImpl(UserDetailMainRepository userDetailMainRepository, PanCardRepository panCardRepository) {
        this.userDetailMainRepository = userDetailMainRepository;
        this.panCardRepository = panCardRepository;
    }

    public FilterAndSearchResponse filterAndSearchDetailsOfClients(String searchedText , KycStatus kycStatus, Integer pageNumber) {
        if(Objects.isNull(pageNumber) || pageNumber < 1) {
            pageNumber = 1 ;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize, Sort.by("updatedAt").descending());
        if(StringUtils.isEmpty(searchedText)) searchedText = "";
        List<KycStatus> kycStatusList ;

        if(Objects.isNull(kycStatus)) {
            kycStatusList = List.of(KycStatus.values());
        } else kycStatusList = List.of(kycStatus);
        log.info("---------------");
        Page<UserDetailMainDao> pageOfUserDetails = customFilterAndSearchDetailsOfUser(searchedText, kycStatusList, pageRequest);

        List<UserDetailMainDao> userDetailedList = pageOfUserDetails.getContent();
        List<UserAdminPageContent> adminPageResponse = new ArrayList<>();
        for (UserDetailMainDao user : userDetailedList) {
            adminPageResponse.add(fillOneByOneUserDetailsInArrayObject(user));
        }
        int totalPage = pageOfUserDetails.getTotalPages();

        PageDetail pageDetail = PageDetail.builder().currentPage(String.valueOf(pageNumber)).pageSize(String.valueOf(pageSize)).totalPage(String.valueOf(totalPage)).totalRecords(String.valueOf(pageOfUserDetails.getTotalElements())).build();
        FilterAndSearchResponse filterAndSearchResponse = FilterAndSearchResponse.builder().pageDetail(pageDetail)
                .userAdminPageContent(adminPageResponse)
                .build();

        return filterAndSearchResponse;
    }

    private Page<UserDetailMainDao>  customFilterAndSearchDetailsOfUser(String searchedText, List<KycStatus> kycStatusList, PageRequest pageRequest) {
        return userDetailMainRepository.searchUserDetailsInDBAndFilterData(searchedText, kycStatusList, pageRequest);
    }

    private UserAdminPageContent fillOneByOneUserDetailsInArrayObject(UserDetailMainDao userDetails) {
        log.info("----------------- findAllUserSepecficeDetails :: user filter data ::: ---------");
        UserAdminPageContent status = new UserAdminPageContent();
        status.setPhoneNumber(userDetails.getPhoneNumber());
        String email = userDetails.getEmail();
        status.setEmail(email != null && !email.isEmpty() ? email : "Not Proceed");
        String clientCode = userDetails.getClientCode();
        status.setClientCode((clientCode != null && !clientCode.isEmpty()) ? clientCode : "Not Created");
        status.setBranch("Nirman");
        status.setReferenceCode("online");
        status.setKycStatus(userDetails.getKycStatus());
        status.setDateEntryTime(userDetails.getCreatedAt());
        status.setUpdateTime(userDetails.getUpdatedAt());
        if (userDetails.getPanCardId() != null) {
            Optional<PanCardDao> pan = panCardRepository.findById(userDetails.getPanCardId());
            if (pan.isPresent()) {
                status.setPanCard(pan.get().getPanNumber());
                status.setCustomerName(pan.get().getName());
            }
        }
        if (userDetails.getDocumentId() != null) {
            status.setAction("Document Complete");
            status.setDocument(userDetails.getDocumentId());
        } else {
            status.setAction("contact");
            status.setDocument("Not Created");
        }
        return  status ;
    }
}
