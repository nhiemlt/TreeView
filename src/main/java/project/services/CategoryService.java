package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.dtos.request.CategoryRequest;
import project.dtos.response.CategoryResponse;
import project.entities.Category;
import project.exceptions.EntityAlreadyExistsException;
import project.exceptions.EntityNotFoundException;
import project.exceptions.ResourceNotFoundException;
import project.mappers.CategoryMapper;
import project.repositories.CategoryRepository;

import java.util.Date;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Page<CategoryResponse> getAllCategories(String search, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categories = categoryRepository.searchCategories(search, pageable);

        return categories.map(CategoryMapper::toCategoryResponse);
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));
        return CategoryMapper.toCategoryResponse(category);
    }

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new ResourceNotFoundException("Category with name " + categoryRequest.getName() + " already exists");
        }
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setStatus(categoryRequest.getStatus());
        category.setCreateDate(new Date());
        category = categoryRepository.save(category);
        return CategoryMapper.toCategoryResponse(category);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));

        if(category.getName().equalsIgnoreCase(categoryRequest.getName()) && categoryRepository.existsByName(categoryRequest.getName())) {
            throw new EntityAlreadyExistsException("Category with name " + categoryRequest.getName() + " already exists");
        }
        category.setName(categoryRequest.getName());
        category.setStatus(categoryRequest.getStatus());
        category = categoryRepository.save(category);
        return CategoryMapper.toCategoryResponse(category);
    }

    public CategoryResponse toggleCategoryStatus(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));
        category.setStatus(!category.getStatus());
        category = categoryRepository.save(category);
        return CategoryMapper.toCategoryResponse(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
        categoryRepository.delete(category);
    }
}