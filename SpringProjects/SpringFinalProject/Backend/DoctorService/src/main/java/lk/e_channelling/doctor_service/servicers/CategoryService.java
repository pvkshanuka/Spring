package lk.e_channelling.doctor_service.servicers;

import lk.e_channelling.doctor_service.dto.ResponseDto;
import lk.e_channelling.doctor_service.models.Category;
import lk.e_channelling.doctor_service.models.Doctor;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CategoryService {

    public ResponseDto save(@RequestBody Category category);

    public ResponseDto update(@RequestBody Category category);

    public ResponseDto delete(@RequestBody int id);

    public List<Category> search(@RequestBody Category category);

    public boolean searchById(int id);

}
