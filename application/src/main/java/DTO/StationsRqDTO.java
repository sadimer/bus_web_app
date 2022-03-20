package DTO;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationsRqDTO {
    @NotNull
    private String name;

    @NotNull
    private String city;
}
