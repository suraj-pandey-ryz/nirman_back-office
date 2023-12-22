package com.back.office.model.payload.filter;

import com.back.office.model.payload.UserAdminPageContent;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class FilterAndSearchResponse {
    private PageDetail pageDetail ;
    private List<UserAdminPageContent> userAdminPageContent;
}
