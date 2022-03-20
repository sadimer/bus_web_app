package DTO;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutesRqDTO {
    @NotNull
    private String name;

    @NotNull
    private CompanyRsDTO company;
}
