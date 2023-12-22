package com.back.office.model.rekyc;

import com.back.office.model.payload.filter.PageDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RekycUserBasicWrapperContent {
    private List<RekycUserBasicAdminContent> rekycUserBasicAdminContent;
    private PageDetail pageDetail;
}
