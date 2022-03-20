package DTO;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutesRsDTO {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private CompanyRsDTO company;
}
