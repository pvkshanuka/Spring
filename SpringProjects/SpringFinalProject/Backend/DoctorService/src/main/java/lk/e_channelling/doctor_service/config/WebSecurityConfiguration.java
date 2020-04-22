package lk.e_channelling.doctor_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET,"/doctor");
        web.ignoring().antMatchers(HttpMethod.GET,"/doctor/find/*");
//        web.ignoring().antMatchers(HttpMethod.POST,"/client/login");


    }


}
