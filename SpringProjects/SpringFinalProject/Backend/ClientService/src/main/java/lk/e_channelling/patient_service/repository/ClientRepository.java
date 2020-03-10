package lk.e_channelling.patient_service.repository;

import lk.e_channelling.patient_service.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Integer> {
}
