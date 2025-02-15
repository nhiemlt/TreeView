package project.dtos.response;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private Boolean status;
    private Long parentId;
    private Date createDate;
}
