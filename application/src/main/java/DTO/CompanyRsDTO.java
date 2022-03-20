package DTO;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRsDTO {
    @NotNull
    private Long id;

    @NotNull
    private String name;
}
