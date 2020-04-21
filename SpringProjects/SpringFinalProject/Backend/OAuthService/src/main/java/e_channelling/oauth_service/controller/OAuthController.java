package e_channelling.oauth_service.controller;

import e_channelling.oauth_service.dto.Login;
import e_channelling.oauth_service.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
            System.out.println(login);
            login.setPassword(passwordEncoder.encode(login.getPassword()));
            return userDetailsService.save(login);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }

    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @RequestMapping(method = RequestMethod.POST, value = "/saveManager")
    public Integer saveManager(@RequestBody Login login){

        try {

            login.setPassword(passwordEncoder.encode(login.getPassword()));
            return userDetailsService.saveManager(login);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }

    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @RequestMapping(method = RequestMethod.POST, value = "/saveAdmin")
    public Integer saveAdmin(@RequestBody Login login){

        try {

            login.setPassword(passwordEncoder.encode(login.getPassword()));
            return userDetailsService.saveAdmin(login);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }

    }

    @RequestMapping(method = RequestMethod.PUT)
    public Boolean updatePassword(@RequestBody Login login){
        System.out.println(login);
        try {

            login.setPassword(passwordEncoder.encode(login.getPassword()));
            return userDetailsService.updatePassword(login);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return false;
        }

    }

}
