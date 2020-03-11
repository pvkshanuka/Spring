package e_channelling.channelling_service.repository;

import e_channelling.channelling_service.models.Channelling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface ChannellingRepository extends JpaRepository<Channelling,Integer> {

    public List<Channelling> findByHospitalAndRoomAndDayAndStartTimeBetween(Integer hospital, String room, String day, Instant startTime, Instant endTime);

}
