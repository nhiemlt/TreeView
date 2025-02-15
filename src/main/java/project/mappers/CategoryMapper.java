package project.mappers;

import project.dtos.request.CategoryRequest;
import project.dtos.response.CategoryResponse;
import project.entities.Category;
import project.repositories.CategoryRepository;

public class CategoryMapper {
    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .status(category.getStatus())
                .createDate(category.getCreateDate())
                .build();
    }
}
