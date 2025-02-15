package project.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private Double price;
    private CategoryResponse category;
    private Boolean status;
    private Date createDate;
}
