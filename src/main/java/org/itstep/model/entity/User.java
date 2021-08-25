package org.itstep.model.entity;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String password;

    public enum ROLE {
        USER, ADMIN, UNKNOWN
    }

    private ROLE role;
}
