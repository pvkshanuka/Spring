package e_channelling.channelling_service.commonModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    private Integer id;

    private String name;

    private String contact;

    List<DoctorCategory> doctorCategories;

    private String status;

    public Doctor(Integer id, @NotNull String name, @NotNull String contact, String status) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.status = status;
    }
}
