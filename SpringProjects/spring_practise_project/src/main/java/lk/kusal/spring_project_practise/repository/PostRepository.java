package lk.kusal.spring_project_practise.repository;

import lk.kusal.spring_project_practise.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}
