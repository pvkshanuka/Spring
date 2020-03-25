package e_channelling.oauth_service.controller;

import e_channelling.oauth_service.dto.Login;
import e_channelling.oauth_service.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OAuthController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.POST)
    public Integer save(@RequestBody Login login){

        try {

            login.setPassword(passwordEncoder.encode(login.getPassword()));
            return userDetailsService.save(login);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }

    }

}
