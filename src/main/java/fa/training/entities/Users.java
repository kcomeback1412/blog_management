package fa.training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Users {

    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String profile;

    @OneToMany(mappedBy = "users")
    private List<Post> posts;

    @OneToMany(mappedBy = "users")
    private List<UserRoles> userRoles;

    public void addPost(Post post) {
        if(posts == null) {
            posts = new ArrayList<>();
        }

        posts.add(post);
    }
}
