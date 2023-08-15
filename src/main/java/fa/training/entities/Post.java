package fa.training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "tbl_post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;


    private String status;

//    @OneToMany
    private String tags;

    @Column(name = "create_time")
    private LocalDate createTime;

    @Column(name = "update_time")
    private LocalDate updateTime;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Users users;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private List<Comments> comments;

    public void addComment(Comments comment) {
        if(comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }

    public String[] tagToArr(){
        String rawTagToArr[] = tags.split(",");

        for (int i = 0; i < rawTagToArr.length; i++) {
            rawTagToArr[i] = rawTagToArr[i].trim();
        }
        return rawTagToArr;
    }

}
