package lk.e_channeling.hospital_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET,"/hospital/findByIdAndStatus/*");
        web.ignoring().antMatchers(HttpMethod.GET,"/hospital");
        web.ignoring().antMatchers(HttpMethod.GET,"/hospital/findById/*");


    }


}
