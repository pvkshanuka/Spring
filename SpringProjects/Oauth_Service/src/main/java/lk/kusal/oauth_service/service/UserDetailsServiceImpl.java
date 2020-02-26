package lk.kusal.oauth_service.service;

import lk.kusal.oauth_service.model.AuthUserDetails;
import lk.kusal.oauth_service.model.User;
import lk.kusal.oauth_service.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Optional<User> optionalUser = userDetailRepository.findByUsername(name);

        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username or Password wrong."));

        UserDetails userDetails = new AuthUserDetails(optionalUser.get());

        new AccountStatusUserDetailsChecker().check(userDetails);

        return userDetails;

    }
}
