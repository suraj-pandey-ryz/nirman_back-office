package com.back.office.model.payload.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PageDetail {
    private String currentPage ;
    private String totalPage ;
    private String totalRecords ;
    private String pageSize ;
}
