package fa.training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_lookup")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lookup {

    @Column(name = "lookup_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer code;

    private String type;

    private Integer position;
}
