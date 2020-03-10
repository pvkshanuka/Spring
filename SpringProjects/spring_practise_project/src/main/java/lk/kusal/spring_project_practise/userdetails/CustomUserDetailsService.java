//package lk.kusal.spring_project_practise.userdetails;
//
//import lk.kusal.spring_project_practise.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class CustomUserDetailsService {
//
//
//    @Autowired
//    private User userDAO;
//
//    public CustomUserDetail loadUserByUsername(String name) throws UsernameNotFoundException, DataAccessException {
//        // returns the get(0) of the user list obtained from the db
//        User domainUser = userDAO.getUser(name);
//
//
//        Set<Role> roles = domainUser.getRole();
//        logger.debug("role of the user" + roles);
//
//        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//        for(Role role: roles){
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//            logger.debug("role" + role + " role.getRole()" + (role.getRole()));
//        }
//
//        CustomUserDetail customUserDetail=new CustomUserDetail();
//        customUserDetail.setUser(domainUser);
//        customUserDetail.setAuthorities(authorities);
//
//        return customUserDetail;
//
//    }
//
//
//}
