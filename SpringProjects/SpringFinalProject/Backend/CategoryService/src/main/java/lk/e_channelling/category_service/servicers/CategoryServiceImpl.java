package lk.e_channelling.category_service.servicers;

import lk.e_channelling.category_service.dto.ResponseDto;
import lk.e_channelling.category_service.exceptions.CategoryException;
import lk.e_channelling.category_service.models.Category;
import lk.e_channelling.category_service.repository.CategoryRepository;
import lk.e_channelling.category_service.support.Validation;
import lk.e_channelling.category_service.support.ValidationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    Validation validation;

    @Override
    public ResponseDto save(Category category) {

        try {

            category.setId(null);

            if (categoryRepository.findAllByCategory(category.getCategory()).isEmpty()) {

                if (validation.stringMinLengthValidator(category.getCategory(), 4)) {

                    category.setStatus("1");
                    Category save = categoryRepository.save(category);

                    if (save.equals(null)) {
                        System.out.println("Category Save Failed.!");
                        return new ResponseDto(false, "Category Save Failed.!");
                    } else {
                        System.out.println("Category Saved Successfully.!");
                        return new ResponseDto(true, "Category Saved Successfully.!");
                    }

                } else {
                    return new ResponseDto(false, "Invalid Category.!");
                }

            } else {
                return new ResponseDto(false, "Category Already Added.!");
            }
        } catch (Exception e) {
            throw new CategoryException("Category save exception occurred in AppointmentServiceImpl.save", e);
        }
    }

    @Override
    public ResponseDto update(Category category) {

        try {

            Optional<Category> optional = categoryRepository.findById(category.getId());

            if (optional.isPresent()) {
                if (optional.get().getStatus().equals("1")) {
                    categoryRepository.save(category);
                    System.out.println("Category Updated Successfully.!");
                    return new ResponseDto(true, "Category Updated Successfully.!");
                } else {
                    return new ResponseDto(true, "Deleted Category.!");
                }
            } else {
                System.out.println("Category Update Failed.!");
                return new ResponseDto(false, "Invalid Category ID.!");
            }
        } catch (Exception e) {
            throw new CategoryException("Category update exception occurred in AppointmentServiceImpl.update", e);
        }
    }

    @Override
    public ResponseDto delete(int id) {

        try {

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

        } catch (Exception e) {
            throw new CategoryException("Category delete exception occurred in AppointmentServiceImpl.delete", e);
        }

    }

    @Override
    public List<Category> search(Category category) {

        try {

            category.setStatus("1");

            ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                    .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.startsWith())
                    .withMatcher("category", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                    .withIgnorePaths("doctors")
                    .withIgnoreNullValues();

            Example<Category> example = Example.of(category, exampleMatcher);
            return categoryRepository.findAll(example);

        } catch (Exception e) {
            throw new CategoryException("Category search exception occurred in AppointmentServiceImpl.search", e);
        }

    }

    @Override
    public List<Category> search() {
        try {


            return categoryRepository.findAllByStatus("1");

        } catch (Exception e) {
            throw new CategoryException("Category search exception occurred in AppointmentServiceImpl.search", e);
        }
    }

    @Override
    public List<Category> searchByIds(List<Integer> ids) {
        try {


            return categoryRepository.findAllById(ids);


        } catch (Exception e) {
            throw new CategoryException("Category searchByIds exception occurred in AppointmentServiceImpl.searchByIds", e);
        }
    }

    @Override
    public boolean searchById(int id) {

        try {

            Example<Category> example = Example.of(new Category(id, null, "1"));
            return categoryRepository.findAll(example).isEmpty();

        } catch (Exception e) {
            throw new CategoryException("Category searchById exception occurred in AppointmentServiceImpl.searchById", e);
        }

    }

    @Override
    public List<Category> searchAllFromIds(List<Integer> integers) {

        try {

            List<Category> categories = categoryRepository.findAllById(integers);



            return categories.stream()
                    .filter(category -> !"0".equals(category.getStatus()))
            .collect(Collectors.toList());

        } catch (Exception e) {
            throw new CategoryException("Category searchAllFromIds exception occurred in AppointmentServiceImpl.searchAllFromIds", e);
        }

    }
}
