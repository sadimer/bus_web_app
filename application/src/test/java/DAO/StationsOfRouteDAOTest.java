package DAO;
import DTO.RoutesRsDTO;
import DTO.StationsOfRouteRqDTO;
import DTO.StationsOfRouteRsDTO;
import DTO.StationsRsDTO;
import entities.Routes;
import entities.Stations;
import entities.StationsOfRoute;
import mapper.RoutesMapper;
import mapper.StationsMapper;
import mapper.StationsOfRouteMapper;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDateTime;
import java.util.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StationsOfRouteDAOTest {
    private StationsOfRouteDAO strt = new StationsOfRouteDAO();
    private StationsDAO stat = new StationsDAO();
    private RoutesDAO route = new RoutesDAO();
    StationsOfRouteMapper mapper = new StationsOfRouteMapper();
    StationsMapper stat_mapper = new StationsMapper();
    RoutesMapper route_mapper = new RoutesMapper();
    private static Long id;

    @Test
    void createStrt() {
        Stations st = stat.getEntityById(1L, Stations.class);
        StationsRsDTO st_dto = stat_mapper.toDto(st);
        Routes rout = route.getEntityById(1L, Routes.class);
        RoutesRsDTO rout_dto = route_mapper.toDto(rout);
        StationsOfRouteRqDTO dto = new StationsOfRouteRqDTO(rout_dto, st_dto, LocalDateTime.now(), LocalDateTime.now(), 1L);
        StationsOfRoute entity = mapper.toStationsOfRoute(dto);
        Assert.assertNotNull(entity);
        // проверка создания
        Assert.assertTrue(strt.create(entity));
        StationsOfRouteRsDTO check_dto = mapper.toDto(entity);
        id = check_dto.getId();
        // проверка атрибутов
        Assert.assertNotNull(id);
        Assert.assertEquals(check_dto.getRoute().getId(), Long.valueOf(1L));
        Assert.assertEquals(check_dto.getStation().getId(), Long.valueOf(1L));
        Assert.assertEquals(check_dto.getSt_index(), Long.valueOf(1L));
    }

    @Test
    void deleteStrt() {
        StationsOfRoute entity = strt.getEntityById(id, StationsOfRoute.class);
        Assert.assertNotNull(entity);
        StationsOfRouteRsDTO check_dto = mapper.toDto(entity);
        // проверим что достали нужную сущность по айди
        Assert.assertEquals(check_dto.getId(), id);
        strt.delete(entity);
        // проверка удаления
        Assert.assertNull(strt.getEntityById(id, StationsOfRoute.class));
    }

    @Test
    public void getAllStrts() {
        List<StationsOfRoute> all = strt.getAll(StationsOfRoute.class);
        Assert.assertNotNull(all);
    }

    @Test
    public void filterStrts() {
        List<StationsOfRoute> result = strt.filter(Map.of("indexFilter",
                Lists.newArrayList(1L)), StationsOfRoute.class);
        Assert.assertEquals(result.size(), 3);
        result.forEach(srt -> Assert.assertEquals(srt.getSt_index(), Long.valueOf(1L)));
        result = strt.filter(Map.of("arrivalFilter",
                Lists.newArrayList(LocalDateTime.of(2022, 1, 1, 0, 0, 0), LocalDateTime.now())), StationsOfRoute.class);
        Assert.assertEquals(result.size(), 2);
        result.forEach(srt -> Assert.assertTrue(srt.getId() == 9 || srt.getId() == 10));
        result = strt.filter(Map.of("departFilter",
                Lists.newArrayList(LocalDateTime.of(2022, 1, 1, 0, 0, 0), LocalDateTime.now())), StationsOfRoute.class);
        Assert.assertEquals(result.size(), 2);
        result.forEach(srt -> Assert.assertTrue(srt.getId() == 9 || srt.getId() == 10));
    }

    @Test
    public void getByJoinStrt() {
        Routes rout = route.getEntityById(2L, Routes.class);
            List<StationsOfRoute> ticks = strt.getByJoin(rout);
        Assert.assertEquals(ticks.size(), 4);
        ticks.forEach(srt -> Assert.assertEquals(srt.getRoute().getId(), Long.valueOf(2L)));
        Stations station = stat.getEntityById(1L, Stations.class);
        ticks = strt.getByJoin(station);
        Assert.assertEquals(ticks.size(), 2);
        ticks.forEach(srt -> Assert.assertEquals(srt.getSt().getId(), Long.valueOf(1L)));
    }
}

