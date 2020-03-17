package lk.e_channelling.doctor_service.repository;

import lk.e_channelling.doctor_service.models.Doctor;
import lk.e_channelling.doctor_service.models.DoctorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    @Query("SELECT DISTINCT new lk.e_channelling.doctor_service.models.Doctor(d.id,d.name,d.contact,d.status) FROM Doctor d JOIN d.doctorCategories c WHERE c.categoryid = :categoryid")
    List<Doctor> findAllDoctorsByCategory(@Param("categoryid") Integer categoryid);

    List<Doctor> findByDoctorCategories(DoctorCategory doctorCategory);

    List<Doctor> findByContact(String contct);

    List<Doctor> findAllByStatus(String status);



}
