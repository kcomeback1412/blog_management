package fa.training.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeEnum {
    POST_STATUS("POST_STATUS"),
    ;
    private String typeName;
}
