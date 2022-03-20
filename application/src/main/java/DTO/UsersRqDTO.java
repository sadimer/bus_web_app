package DTO;

import lombok.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersRqDTO {
    @NotNull
    private String name;

    @NotNull
    private String passwd;

    @NotNull
    private String login;

    @NotNull
    private Boolean admin;
}
