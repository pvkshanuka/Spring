package lk.e_channelling.category_service.servicers;

import lk.e_channelling.category_service.dto.ResponseDto;
import lk.e_channelling.category_service.models.Category;
import lk.e_channelling.category_service.models.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CategoryService {

    public ResponseDto save(@RequestBody Category category);

    public ResponseDto update(@RequestBody Category category);

    public ResponseDto delete(@RequestBody int id);

    public List<Category> search(@RequestBody Category category);

    public List<Category> search();

    public List<Category> searchByIds(@RequestBody List<Integer> ids);

    public boolean searchById(int id);

    public List<Category> searchAllFromIds(List<Integer> integers);

    public void result(int id);

    public List<Result> findByName(String name);

}
