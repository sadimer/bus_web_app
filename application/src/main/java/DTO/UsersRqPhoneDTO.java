package DTO;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersRqPhoneDTO {
    @NotNull
    private String name;

    @NotNull
    private String phone;

    @NotNull
    private String passwd;

    @NotNull
    private String login;

    @NotNull
    private Boolean admin;
}
