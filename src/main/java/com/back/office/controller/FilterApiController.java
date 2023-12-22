package com.back.office.controller;

import com.back.office.enums.KycStatus;
import com.back.office.model.payload.filter.FilterAndSearchResponse;
import com.back.office.service.FilterAndSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@Slf4j
public class FilterApiController {

    private final FilterAndSearchService filterAndSearchService;

    public FilterApiController(FilterAndSearchService filterAndSearchService) {
        this.filterAndSearchService = filterAndSearchService;
    }

    @GetMapping("/users/filter")
    public ResponseEntity<FilterAndSearchResponse> get(@RequestParam(name = "status" , required = false)KycStatus kycStatus, @RequestParam(name = "search", required = false)  String search , @RequestParam(name = "page", required = false) Integer page) {
        return ResponseEntity.ok(filterAndSearchService.filterAndSearchDetailsOfClients(search, kycStatus , page));
    }

}
