package DAO;
import DTO.RoutesRsDTO;
import DTO.StationsRsDTO;
import DTO.SubroutesRqDTO;
import DTO.SubroutesRsDTO;
import entities.Routes;
import entities.Stations;
import entities.Subroutes;
import mapper.RoutesMapper;
import mapper.StationsMapper;
import mapper.SubroutesMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubroutesDAOTest {
    private SubroutesDAO subroutes = new SubroutesDAO();
    private StationsDAO stat = new StationsDAO();
    private RoutesDAO route = new RoutesDAO();
    SubroutesMapper mapper = new SubroutesMapper();
    StationsMapper stat_mapper = new StationsMapper();
    RoutesMapper route_mapper = new RoutesMapper();
    private static Long id;

    @Test
    void createSubr() {
        Stations dep_st = stat.getEntityById(2L, Stations.class);
        StationsRsDTO dep_st_dto = stat_mapper.toDto(dep_st);
        Stations arr_st = stat.getEntityById(1L, Stations.class);
        StationsRsDTO arr_st_dto = stat_mapper.toDto(arr_st);
        Routes rout = route.getEntityById(1L, Routes.class);
        RoutesRsDTO rout_dto = route_mapper.toDto(rout);
        SubroutesRqDTO dto = new SubroutesRqDTO(rout_dto, arr_st_dto, dep_st_dto);
        Subroutes entity = mapper.toSubroutes(dto);
        Assert.assertNotNull(entity);
        // проверка создания
        Assert.assertTrue(subroutes.create(entity));
        SubroutesRsDTO check_dto = mapper.toDto(entity);
        id = check_dto.getId();
        // проверка атрибутов
        Assert.assertNotNull(id);
        Assert.assertEquals(check_dto.getRoute().getId(), Long.valueOf(1L));
        Assert.assertEquals(check_dto.getArrival().getId(), Long.valueOf(1L));
        Assert.assertEquals(check_dto.getDepart().getId(), Long.valueOf(2L));
    }

    @Test
    void deleteSubr() {
        Subroutes entity = subroutes.getEntityById(id, Subroutes.class);
        Assert.assertNotNull(entity);
        SubroutesRsDTO check_dto = mapper.toDto(entity);
        // проверим что достали нужную сущность по айди
        Assert.assertEquals(check_dto.getId(), id);
        subroutes.delete(entity);
        // проверка удаления
        Assert.assertNull(subroutes.getEntityById(id, Subroutes.class));
    }

    @Test
    public void getAllSubrs() {
        List<Subroutes> all = subroutes.getAll(Subroutes.class);
        Assert.assertNotNull(all);
    }

    @Test
    public void getByJoinSubr() {
        Routes rout = route.getEntityById(1L, Routes.class);
        List<Subroutes> srt = subroutes.getByJoin(rout);
        Assert.assertEquals(srt.size(), 1);
        srt.forEach(srtr -> Assert.assertEquals(srtr.getRoute().getId(), Long.valueOf(1L)));
        Stations station = stat.getEntityById(5L, Stations.class);
        srt = subroutes.getByJoinDep(station);
        Assert.assertEquals(srt.size(), 3);
        srt.forEach(srtr -> Assert.assertEquals(srtr.getDepart_st().getId(), Long.valueOf(5L)));
        station = stat.getEntityById(1L, Stations.class);
        srt = subroutes.getByJoinArr(station);
        Assert.assertEquals(srt.size(), 5);
        srt.forEach(srtr -> Assert.assertEquals(srtr.getArrival_st().getId(), Long.valueOf(1L)));
    }
}
