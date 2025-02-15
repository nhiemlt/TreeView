package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.dtos.request.ProductRequest;
import project.dtos.response.ProductResponse;
import project.entities.Category;
import project.entities.Product;
import project.exceptions.EntityAlreadyExistsException;
import project.exceptions.EntityNotFoundException;
import project.mappers.ProductMapper;
import project.repositories.CategoryRepository;
import project.repositories.ProductRepository;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        return ProductMapper.toProductResponse(product);
    }

    public Page<ProductResponse> getAllProducts(String search, List<Long> categoryIds, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = productRepository.searchProducts(search, categoryIds, pageable);

        return products.map(ProductMapper::toProductResponse);
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        if (productRepository.existsByName(productRequest.getName())) {
            throw new EntityAlreadyExistsException("Product with name " + productRequest.getName() + " already exists");
        }
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + productRequest.getCategoryId()));
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCategory(category);
        product.setStatus(productRequest.getStatus());
        product.setCreateDate(new Date());
        product = productRepository.save(product);
        return ProductMapper.toProductResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

        if (!product.getName().equalsIgnoreCase(productRequest.getName()) && productRepository.existsByName(productRequest.getName())) {
            throw new EntityAlreadyExistsException("Product with name " + productRequest.getName() + " already exists");
        }
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + productRequest.getCategoryId()));
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCategory(category);
        product.setStatus(productRequest.getStatus());
        product = productRepository.save(product);
        return ProductMapper.toProductResponse(product);
    }

    public ProductResponse toggleProductStatus(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        product.setStatus(!product.getStatus());
        product = productRepository.save(product);
        return ProductMapper.toProductResponse(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        productRepository.delete(product);
    }
}