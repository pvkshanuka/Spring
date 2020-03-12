package e_channelling.channelling_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChannellingServiceApplication {

    //	In Mins
    public static final int CHANNELLING_DURATION_MIN = 60;
    //	In Mins
    public static final int CHANNELLING_DURATION_MAX = 60 * 4;

    public static final String DOMAIN_APPOINTMENT_SERVICE = "localhost:8050";

    public static final String DOMAIN_HOSPITAL_SERVICE = "localhost:8010";


    public static void main(String[] args) {
        SpringApplication.run(ChannellingServiceApplication.class, args);
    }


}
