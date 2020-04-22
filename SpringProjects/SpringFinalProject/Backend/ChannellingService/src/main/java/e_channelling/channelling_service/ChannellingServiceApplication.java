package e_channelling.channelling_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableEurekaClient
public class ChannellingServiceApplication {

    //	In Mins
    public static final int CHANNELLING_DURATION_MIN = 60;
    //	In Mins
    public static final int CHANNELLING_DURATION_MAX = 60 * 4;

    public static final String DOMAIN_APPOINTMENT_SERVICE = "localhost:8050";

    public static final String DOMAIN_HOSPITAL_SERVICE = "localhost:8010";

    public static final String DOMAIN_DOCTOR_SERVICE = "localhost:8020";

    public static final String DOMAIN_CATGORY_SERVICE = "192.168.1.107:8030";

    public static final String DOMAIN_CLIENT_SERVICE = "192.168.1.107:8040";




    public static void main(String[] args) {
        SpringApplication.run(ChannellingServiceApplication.class, args);
    }


}
