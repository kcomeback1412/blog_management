package fa.training.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class
UserRoles implements Serializable {

    @EmbeddedId
    private UserRolesId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "roles_id")
    private Roles roles;

}
