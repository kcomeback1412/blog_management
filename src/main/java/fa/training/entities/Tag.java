package fa.training.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_tag")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Tag {

    @Column(name = "tag_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(columnDefinition = "INT DEFAULT 1")
    private Integer frequency;

//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private Post post;
}
