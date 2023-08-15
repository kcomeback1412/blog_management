package fa.training.auth;


import fa.training.entities.UserRoles;
import fa.training.entities.Users;
import fa.training.repositories.UserRolesRepository;
import fa.training.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CustomUsersDetailService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Transactional(rollbackOn = SQLException.class)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users usersDB = usersRepository.findByUsername(username);
        List<UserRoles> rolesList = userRolesRepository.findAllByUsers(usersDB);
        usersDB.setUserRoles(rolesList);
        return new CustomUsersDetail(usersDB);
    }
}
