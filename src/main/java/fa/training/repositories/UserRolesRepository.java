package fa.training.repositories;

import fa.training.entities.UserRoles;
import fa.training.entities.UserRolesId;
import fa.training.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRoles, UserRolesId> {

    List<UserRoles> findAllByUsers(Users users);
}
