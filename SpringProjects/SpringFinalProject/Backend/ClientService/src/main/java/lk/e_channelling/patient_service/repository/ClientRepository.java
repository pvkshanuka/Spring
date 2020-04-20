package lk.e_channelling.patient_service.repository;

import lk.e_channelling.patient_service.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Integer> {

    public Optional<Client> findByEmail(String email);
    public Optional<Client> findByEmailAndHospital(String email, Integer hospital);

    public List<Client> findByHospitalAndStatusNot(Integer hospital,String status);

}
