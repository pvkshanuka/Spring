package lk.e_channelling.category_service.repository;

import lk.e_channelling.category_service.models.Category;
import lk.e_channelling.category_service.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result,Integer> {
    List<Result> findByNameContainingIgnoreCase(String name);
}
