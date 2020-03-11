package lk.e_channelling.category_service.repository;

import lk.e_channelling.category_service.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    List<Category> findAllByCategory(String category);
}
