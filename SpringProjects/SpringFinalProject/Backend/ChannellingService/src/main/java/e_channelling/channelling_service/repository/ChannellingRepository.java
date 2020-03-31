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

    public List<Channelling> findByStatus(String status);

    public List<Channelling> findByStatusAndHospitalAndDoctorAndStartTimeBetween(String status,Integer hospital, Integer doctor, Instant startTime, Instant endTime);

    public Optional<Channelling> findByIdAndStatus(Integer id,String status);

    List<Channelling> findByStatusAndHospitalAndDoctor(String s, Integer hospital, Integer doctor);

    List<Channelling> findByStatusAndDoctorAndStartTimeBetween(String s, Integer doctor, Instant date, Instant plusSeconds);

    List<Channelling> findByStatusAndHospitalAndStartTimeBetween(String s, Integer hospital, Instant date, Instant plusSeconds);

    List<Channelling> findByStatusAndHospital(String s, Integer hospital);

    List<Channelling> findByStatusAndDoctor(String s, Integer doctor);

    List<Channelling> findByStatusAndStartTimeBetween(String s, Instant date, Instant plusSeconds);
}
