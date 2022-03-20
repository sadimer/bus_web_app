package DTO;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubroutesRqDTO {
    @NotNull
    private RoutesRsDTO route;

    @NotNull
    private StationsRsDTO arrival;

    @NotNull
    private StationsRsDTO depart;
}
