package project.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Category ID is mandatory")
    @Min(value = 1, message = "Category ID must be greater than or equal to 1")
    private Long categoryId;

    @NotNull(message = "Status is mandatory")
    private Boolean status;
}
