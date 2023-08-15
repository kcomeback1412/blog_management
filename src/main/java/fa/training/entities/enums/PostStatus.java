package fa.training.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostStatus {
    DRAFT("Draft"), // You must insert to tbl_Lookup with name = "Draft" and type = "POST_STATUS"
    PUBLISHED("Published"), // You must insert to tbl_Lookup with name = "Published" and type = "POST_STATUS"
    OUT_DATED("Outdated"), // You must insert to tbl_Lookup with name = "Outdated" and type = "POST_STATUS"
    ;

   String name;
}
