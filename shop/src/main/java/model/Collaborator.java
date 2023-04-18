package model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Collaborator {
    private String userName;
    private String email;
    private String password;
    private String phone;
    private Address address;

}
