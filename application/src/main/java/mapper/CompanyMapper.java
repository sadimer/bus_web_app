package mapper;

import DTO.CompanyRqDTO;
import DTO.CompanyRsDTO;
import entities.Company;
import org.modelmapper.ModelMapper;
import DTO.*;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public Company toCompany(CompanyRqDTO dto) {
        var mapper = new ModelMapper();
        return mapper.map(dto, Company.class);
    }

    public Company toCompany(CompanyRsDTO dto) {
        var mapper = new ModelMapper();
        return mapper.map(dto, Company.class);
    }

    public CompanyRsDTO toDto(Company company) {
        var mapper = new ModelMapper();
        return mapper.map(company, CompanyRsDTO.class);
    }
}
