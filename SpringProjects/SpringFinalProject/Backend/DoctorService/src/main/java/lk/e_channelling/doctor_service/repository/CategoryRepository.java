package lk.e_channelling.doctor_service.repository;

import lk.e_channelling.doctor_service.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
