package DTO;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationsOfRouteRsDTO {
    @NotNull
    private Long id;

    @NotNull
    private RoutesRsDTO route;

    @NotNull
    private StationsRsDTO station;

    @NotNull
    private LocalDateTime arrival;

    @NotNull
    private LocalDateTime dep;

    @NotNull
    private Long st_index;
}
