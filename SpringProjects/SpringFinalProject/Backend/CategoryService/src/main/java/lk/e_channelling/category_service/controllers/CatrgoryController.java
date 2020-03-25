package lk.e_channelling.category_service.controllers;

import lk.e_channelling.category_service.dto.ResponseDto;
import lk.e_channelling.category_service.models.Category;
import lk.e_channelling.category_service.servicers.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CatrgoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto save(@RequestBody Category category) {

        try {

            return categoryService.save(category);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Category Save Failed.!");
        }

    }
//
//    @RequestMapping(method = RequestMethod.PUT)
//    public ResponseDto update(@RequestBody Category category) {
//
//        try {
//            category.setStatus("1");
//            return categoryService.update(category);
//
//        } catch (Exception e) {
//            System.out.println("log exception");
//            return new ResponseDto(false, "Category Update Failed.!");
//        }
//
//    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseDto delete(@PathVariable int id) {

        try {

            return categoryService.delete(id);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Category Delete Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public List<Category> searchExample(@RequestBody Category category) {
        try {

            return categoryService.search(category);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    public List<Category> searchAll() {
        try {

            return categoryService.search();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Category test() {

        Category category = new Category();
        category.setId(1);
        category.setCategory("cat1");
        category.setStatus("1");

        return category;

    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkCategories")
    public List<Category> checkCategories(@RequestBody List<Integer> integers) {

        return categoryService.searchAllFromIds(integers);
    }


}
