package lk.e_channelling.doctor_service.servicers;

import lk.e_channelling.doctor_service.dto.ResponseDto;
import lk.e_channelling.doctor_service.models.Category;
import lk.e_channelling.doctor_service.models.Doctor;
import lk.e_channelling.doctor_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ResponseDto save(Category category) {

        Category save = categoryRepository.save(category);
        if (save.getId().equals(null)) {
            System.out.println("Category Save Failed.!");
            return new ResponseDto(false, "Category Save Failed.!");
        } else {
            System.out.println("Category Saved Successfully.!");
            return new ResponseDto(true, "Category Saved Successfully.!");
        }

    }

    @Override
    public ResponseDto update(Category category) {

        Optional<Category> optional = categoryRepository.findById(category.getId());

        if (optional.isPresent()) {
            if (optional.get().getStatus().equals("1")) {
                categoryRepository.save(category);
                System.out.println("Category Updated Successfully.!");
                return new ResponseDto(true, "Category Updated Successfully.!");
            }else{
                return new ResponseDto(true, "Deleted Category.!");
            }
        } else {
            System.out.println("Category Update Failed.!");
            return new ResponseDto(false, "Invalid Category ID.!");
        }

    }

    @Override
    public ResponseDto delete(int id) {

        Optional<Category> optional = categoryRepository.findById(id);

        if (optional.isPresent()) {
            Category category = optional.get();
            category.setStatus("0");
            categoryRepository.save(category);
            System.out.println("Category Deleted Successfully.!");
            return new ResponseDto(true, "Category Deleted Successfully.!");
        } else {
            System.out.println("Category Delete Failed.!");
            return new ResponseDto(false, "Invalid Category ID.!");
        }

    }

    @Override
    public List<Category> search(Category category) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("category", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withIgnorePaths("doctors")
                .withIgnoreNullValues();

        Example<Category> example = Example.of(category, exampleMatcher);
        return categoryRepository.findAll(example);

    }

    @Override
    public boolean searchById(int id){

        Example<Category> example = Example.of(new Category(id,null,null,"1"));
        return categoryRepository.findAll(example).isEmpty();

    }
}
