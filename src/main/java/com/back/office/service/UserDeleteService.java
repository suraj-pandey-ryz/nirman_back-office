package com.back.office.service;

import com.back.office.model.dao.AadharDataDao;
import com.back.office.model.dao.UserDetailMainDao;
import com.back.office.repository.AadharDataRepository;
import com.back.office.repository.AddressRepository;
import com.back.office.repository.PanCardRepository;
import com.back.office.repository.UserDetailMainRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserDeleteService {

    private final UserDetailMainRepository userDetailMainRepository ;
    private final AadharDataRepository aadharDataRepository ;
    private final AddressRepository addressRepository;
    private final PanCardRepository panCardRepository;

    public UserDeleteService(UserDetailMainRepository userDetailMainRepository, AadharDataRepository aadharDataRepository, AddressRepository addressRepository, PanCardRepository panCardRepository) {
        this.userDetailMainRepository = userDetailMainRepository;
        this.aadharDataRepository = aadharDataRepository;
        this.addressRepository = addressRepository;
        this.panCardRepository = panCardRepository;
    }

    public String deleteUserPhoneNumber(String phoneNumber) {
        Optional<UserDetailMainDao> users =userDetailMainRepository.findByPhoneNumber(phoneNumber);
        if(users.isPresent()) {
            userDetailMainRepository.delete(users.get());
            return "success" ;
        }
        return "failure" ;
    }
}
