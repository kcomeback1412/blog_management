package fa.training.entities;

import fa.training.entities.enums.CommentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comments {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String content;

    @Enumerated(EnumType.ORDINAL)
    private CommentStatus status;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    private String author;

    private String email;

    private String url;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


}
