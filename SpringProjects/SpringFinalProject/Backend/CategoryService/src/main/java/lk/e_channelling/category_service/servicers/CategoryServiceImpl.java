package lk.e_channelling.category_service.servicers;

import lk.e_channelling.category_service.dto.ResponseDto;
import lk.e_channelling.category_service.exceptions.CategoryException;
import lk.e_channelling.category_service.models.Category;
import lk.e_channelling.category_service.models.Result;
import lk.e_channelling.category_service.repository.CategoryRepository;
import lk.e_channelling.category_service.repository.ResultRepository;
import lk.e_channelling.category_service.support.Validation;
import lk.e_channelling.category_service.support.ValidationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    Validation validation;

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

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


            List<Category> collect = categories.stream()
                    .filter(category -> !"0".equals(category.getStatus()))
                    .collect(Collectors.toList());

            System.out.println(collect);

            return collect;

        } catch (Exception e) {
            throw new CategoryException("Category searchAllFromIds exception occurred in AppointmentServiceImpl.searchAllFromIds", e);
        }

    }

    public void result(int id) {

        try {
            System.out.println("Checking ID : "+id);
            HttpHeaders httpHeaders = new HttpHeaders();


            HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);

            ResponseEntity<String> responseEntity = restTemplate.exchange("https://result.doenets.lk/result/service/OlResult?index=" + id + "&nic=", HttpMethod.GET, httpEntity, String.class);

            String responseEntityBody = responseEntity.getBody();


            if (!responseEntityBody.contains("errMsge")) {
                System.out.println(responseEntityBody);

                String name = responseEntityBody.split("name")[1].split("\"")[2].split("\"")[0];
                String indexNo = responseEntityBody.split("indexNo")[1].split("\"")[2].split("\"")[0];

                String s1 = responseEntityBody.split("subjectName\"")[1].split("\"")[1].split("\"")[0] + " - " + responseEntityBody.split("subjectResult\"")[1].split("\"")[1].split("\"")[0];
                String s2 = responseEntityBody.split("subjectName\"")[2].split("\"")[1].split("\"")[0] + " - " + responseEntityBody.split("subjectResult\"")[2].split("\"")[1].split("\"")[0];
                String s3 = responseEntityBody.split("subjectName\"")[3].split("\"")[1].split("\"")[0] + " - " + responseEntityBody.split("subjectResult\"")[3].split("\"")[1].split("\"")[0];
                String s4 = responseEntityBody.split("subjectName\"")[4].split("\"")[1].split("\"")[0] + " - " + responseEntityBody.split("subjectResult\"")[4].split("\"")[1].split("\"")[0];
                String s5 = responseEntityBody.split("subjectName\"")[5].split("\"")[1].split("\"")[0] + " - " + responseEntityBody.split("subjectResult\"")[5].split("\"")[1].split("\"")[0];
                String s6 = responseEntityBody.split("subjectName\"")[6].split("\"")[1].split("\"")[0] + " - " + responseEntityBody.split("subjectResult\"")[6].split("\"")[1].split("\"")[0];
                String s7 = responseEntityBody.split("subjectName\"")[7].split("\"")[1].split("\"")[0] + " - " + responseEntityBody.split("subjectResult\"")[7].split("\"")[1].split("\"")[0];
                String s8 = responseEntityBody.split("subjectName\"")[8].split("\"")[1].split("\"")[0] + " - " + responseEntityBody.split("subjectResult\"")[8].split("\"")[1].split("\"")[0];
                String s9 = responseEntityBody.split("subjectName\"")[9].split("\"")[1].split("\"")[0] + " - " + responseEntityBody.split("subjectResult\"")[9].split("\"")[1].split("\"")[0];

                Result result = new Result();
                result.setIdno(indexNo);
                result.setName(name);

                result.setReligion(s1);
                result.setLang(s2);
                result.setEng(s3);
                result.setMaths(s4);
                result.setHis(s5);
                result.setSci(s6);
                result.setSubFirst(s7);
                result.setSubSecond(s8);
                result.setSubThird(s9);

                System.out.println(result);

                System.out.println(indexNo);
                System.out.println(name);

                System.out.println(s1);
                System.out.println(s2);
                System.out.println(s3);
                System.out.println(s4);
                System.out.println(s5);
                System.out.println(s6);
                System.out.println(s7);
                System.out.println(s8);
                System.out.println(s9);

                resultRepository.save(result);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Result> findByName(String name){
        System.out.println(name);
        List<Result> byNameLike = resultRepository.findByNameContainingIgnoreCase(name);
        System.out.println(byNameLike);
        return byNameLike;

    }

}
