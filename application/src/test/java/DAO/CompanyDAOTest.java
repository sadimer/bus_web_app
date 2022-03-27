package DAO;
import DTO.CompanyRqDTO;
import DTO.CompanyRsDTO;
import com.google.common.collect.Lists;
import entities.Company;
import entities.Routes;
import mapper.CompanyMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CompanyDAOTest {
    private CompanyDAO company = new CompanyDAO();
    CompanyMapper mapper = new CompanyMapper();
    private static Long id;

    @Test
    void createComp() {
        CompanyRqDTO dto = new CompanyRqDTO("Перевозчик номер 1");
        Company entity = mapper.toCompany(dto);
        Assert.assertNotNull(entity);
        // проверка создания
        Assert.assertTrue(company.create(entity));
        CompanyRsDTO check_dto = mapper.toDto(entity);
        id = check_dto.getId();
        // проверка атрибутов
        Assert.assertNotNull(id);
        Assert.assertEquals(check_dto.getName(), "Перевозчик номер 1");
    }

    @Test
    void deleteComp() {
        Company entity = company.getEntityById(id, Company.class);
        Assert.assertNotNull(entity);
        CompanyRsDTO check_dto = mapper.toDto(entity);
        // проверим что достали нужную сущность по айди
        Assert.assertEquals(check_dto.getId(), id);
        company.delete(entity);
        // проверка удаления
        Assert.assertNull(company.getEntityById(id, Company.class));
    }

    @Test
    void updateComp() {
        Company old_entity = company.getEntityById(1L, Company.class);
        CompanyRsDTO dto = new CompanyRsDTO(1L,"Рога и копыта");
        Company entity = mapper.toCompany(dto);
        Assert.assertNotNull(entity);
        company.update(entity);
        CompanyRsDTO check_dto = mapper.toDto(entity);
        // проверка атрибутов
        Assert.assertEquals(check_dto.getId(), Long.valueOf(1L));
        Assert.assertEquals(check_dto.getName(), "Рога и копыта");
        company.update(old_entity);
    }

    @Test
    public void getAllComps() {
        List<Company> all = company.getAll(Company.class);
        Assert.assertNotNull(all);
    }

    @Test
    public void filterComps() {
        List<Company> result = company.filter(Map.of("nameFilter",
                Lists.newArrayList("ЦЭНКИ")), Company.class);
        Assert.assertEquals(result.size(), 1);
        result.forEach(comp -> Assert.assertTrue(comp.getName().contains("ЦЭНКИ")));
    }
}

