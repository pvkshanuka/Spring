package e_channelling.channelling_service.repository;

import e_channelling.channelling_service.models.Channelling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChannellingRepository extends JpaRepository<Channelling,Integer> {

    public List<Channelling> findByHospitalAndRoomAndStartTimeBetween(Integer hospital, String room, Instant startTime, Instant endTime);

    public List<Channelling> findByHospitalAndDoctorAndStartTimeBetween(Integer hospital, Integer doctor, Instant startTime, Instant endTime);


    public Optional<Channelling> findByIdAndStatus(Integer id,String status);

}
