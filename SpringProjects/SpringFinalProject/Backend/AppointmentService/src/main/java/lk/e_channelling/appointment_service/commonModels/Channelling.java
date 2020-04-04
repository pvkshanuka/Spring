package lk.e_channelling.appointment_service.commonModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Channelling {

    private Integer id;

    private Integer hospital;

    private Integer doctor;

    private String room;

    private Double price;

    private Instant startTime;

    private Instant endTime;

    private String status;

}
