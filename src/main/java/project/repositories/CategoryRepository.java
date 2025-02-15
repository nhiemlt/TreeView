package project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE :search IS NULL OR  LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Category> searchCategories(@Param("search") String search, Pageable pageable);

    boolean existsByName(String name);
}