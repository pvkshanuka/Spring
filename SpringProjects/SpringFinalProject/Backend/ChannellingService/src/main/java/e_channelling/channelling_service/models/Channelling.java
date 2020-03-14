package e_channelling.channelling_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Channelling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer hospital;

    @NotNull
    private Integer doctor;

    @NotNull
    private String room;

    @NotNull
    private Double price;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    @NotNull
    private String day;

    @NotNull
    private String status;

}
