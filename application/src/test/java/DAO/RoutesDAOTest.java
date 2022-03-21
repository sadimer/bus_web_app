package DAO;
import DTO.*;
import com.google.common.collect.Lists;
import entities.Company;
import entities.Routes;
import entities.Stations;
import mapper.CompanyMapper;
import mapper.RoutesMapper;
import mapper.StationsMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoutesDAOTest {
    private RoutesDAO routes = new RoutesDAO();
    private CompanyDAO company = new CompanyDAO();
    RoutesMapper mapper = new RoutesMapper();
    CompanyMapper comp_mapper = new CompanyMapper();
    private static Long id;

    @Test
    void createRout() {
        Company comp = company.getEntityById(1L, Company.class);
        if (comp == null) {
            CompanyRqDTO dto = new CompanyRqDTO("Перевозчик номер 1");
            comp = comp_mapper.toCompany(dto);
            Assert.assertTrue(company.create(comp));
        }
        CompanyRsDTO comp_dto = comp_mapper.toDto(comp);
        RoutesRqDTO dto = new RoutesRqDTO("9К", comp_dto);
        Routes entity = mapper.toRoutes(dto);
        Assert.assertNotNull(entity);
        // проверка создания
        Assert.assertTrue(routes.create(entity));
        RoutesRsDTO check_dto = mapper.toDto(entity);
        id = check_dto.getId();
        // проверка атрибутов
        Assert.assertNotNull(id);
        Assert.assertEquals(check_dto.getName(), "9К");
        Assert.assertEquals(check_dto.getCompany().getId(), Long.valueOf(1L));
    }

    @Test
    void deleteRout() {
        Routes entity = routes.getEntityById(id, Routes.class);
        Assert.assertNotNull(entity);
        RoutesRsDTO check_dto = mapper.toDto(entity);
        // проверим что достали нужную сущность по айди
        Assert.assertEquals(check_dto.getId(), id);
        routes.delete(entity);
        // проверка удаления
        Assert.assertNull(routes.getEntityById(id, Routes.class));
    }

    @Test
    public void getAllRouts() {
        List<Routes> all = routes.getAll(Routes.class);
        Assert.assertNotNull(all);
    }

    @Test
    public void filterRouts() {
        this.createRout();
        List<Routes> result = routes.filter(Map.of("nameFilter",
                Lists.newArrayList("%K9%")), Routes.class);
        result.forEach(route -> Assert.assertTrue(route.getName().contains("K9")));
    }
}
