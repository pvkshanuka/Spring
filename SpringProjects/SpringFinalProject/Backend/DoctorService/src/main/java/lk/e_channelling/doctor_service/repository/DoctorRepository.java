package lk.e_channelling.doctor_service.repository;

import lk.e_channelling.doctor_service.models.Doctor;
import lk.e_channelling.doctor_service.models.DoctorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

//    @Query("SELECT dc FROM echannelling_doctor.DoctorCategory dc WHERE dc.categoryid = :categoryid")
//    List<DoctorCategory> findAllDoctorsByCategory(@Param("categoryid") Integer categoryid);
    List<Doctor> findByDoctorCategories(DoctorCategory doctorCategory);

}
