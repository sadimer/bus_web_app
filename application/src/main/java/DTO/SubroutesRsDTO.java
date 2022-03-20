package DTO;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubroutesRsDTO {
    @NotNull
    private Long id;

    @NotNull
    private RoutesRsDTO route;

    @NotNull
    private StationsRsDTO arrival;

    @NotNull
    private StationsRsDTO depart;
}
