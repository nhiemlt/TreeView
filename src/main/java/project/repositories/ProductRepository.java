package project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.entities.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE "
            + "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) "
            + "AND (:categoryIds IS NULL OR p.category.id IN :categoryIds)")
    Page<Product> searchProducts(@Param("search") String search,
                                 @Param("categoryIds") List<Long> categoryIds,
                                 Pageable pageable);
}
