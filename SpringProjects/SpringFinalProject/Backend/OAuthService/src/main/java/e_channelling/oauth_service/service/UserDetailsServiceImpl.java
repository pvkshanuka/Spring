package e_channelling.oauth_service.service;

import e_channelling.oauth_service.dto.Login;
import e_channelling.oauth_service.model.AuthUserDetail;
import e_channelling.oauth_service.model.Role;
import e_channelling.oauth_service.model.User;
import e_channelling.oauth_service.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optional = userDetailRepository.findByUsername(username);

        optional.orElseThrow(() -> new UsernameNotFoundException("Invalid Username or Password"));

        UserDetails userDetails = new AuthUserDetail(optional.get());
        new AccountStatusUserDetailsChecker().check(userDetails);
        return userDetails;

    }

    public Integer save(Login login) {

            if (userDetailRepository.findByUsername(login.getUsername()).isPresent()) {
                return null;
            } else {

                List<Role> roleList = new ArrayList<>();
                Role role = new Role();
                role.setId(login.getRole_id());
                roleList.add(role);

                User save = userDetailRepository.save(new User(null, login.getUsername(), login.getPassword(), login.getUsername(), true, true, true, true, roleList));
                System.out.println("SAVED USER ID- " + save.getId());
                return save.getId();
            }
    }

}
