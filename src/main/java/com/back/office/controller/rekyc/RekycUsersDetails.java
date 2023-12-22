package com.back.office.controller.rekyc;


import com.back.office.enums.RekycStatus;
import com.back.office.model.payload.dto.RekycUserDataCustom;
import com.back.office.model.rekyc.RekycUserBasicWrapperContent;
import com.back.office.service.rekyc.RekycUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/rekyc/users")
public class RekycUsersDetails {
    private final RekycUserDetailsService rekycUserDetailsService;

    public RekycUsersDetails(RekycUserDetailsService rekycUserDetailsService) {
        this.rekycUserDetailsService = rekycUserDetailsService;
    }

    @GetMapping("/details")
    public ResponseEntity<RekycUserBasicWrapperContent> getDetailsOfRekycUsers(@RequestParam(name = "search", required = false)  String search , @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "rekycstatus",  required = false) RekycStatus rekycStatus) {
        return ResponseEntity.ok(rekycUserDetailsService.getFilterListOfRekycUser(search, page, rekycStatus));
    }

    @GetMapping("/single/user/{xuserid}")
    public ResponseEntity<RekycUserDataCustom> getRekycDetailsOfSingleUser(@PathVariable(name = "xuserid") String xuserid) {
        return ResponseEntity.ok(rekycUserDetailsService.detailsOfRekycUsers(UUID.fromString(xuserid)));
    }
}
