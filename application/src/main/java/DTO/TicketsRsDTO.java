package DTO;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketsRsDTO {
    @NotNull
    private Long id;

    @NotNull
    private UsersRsDTO user;

    @NotNull
    private SubroutesRsDTO sub;

    @NotNull
    private Long seats;

    @NotNull
    private Double price;
}
