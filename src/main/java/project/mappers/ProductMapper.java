package project.mappers;

import project.dtos.response.CategoryResponse;
import project.dtos.response.ProductResponse;
import project.entities.Product;

public class ProductMapper {
    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .category(CategoryMapper.toCategoryResponse(product.getCategory()))
                .status(product.getStatus())
                .createDate(product.getCreateDate())
                .build();
    }
}
