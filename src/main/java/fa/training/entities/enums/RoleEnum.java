package fa.training.entities.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
//    ADMIN (1, "ADMIN"),
//    STAFF(2, "STAFF"),
//    MEMBER(3, "MEMBER"),
    USER(1, "USER"), //You must insert "USER_ROLE" to database with id = 1
    ;
    private int id;
    private String name;

    private RoleEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
