package DAO;
import DTO.StationsRqDTO;
import DTO.StationsRsDTO;
import com.google.common.collect.Lists;
import entities.Stations;
import mapper.StationsMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class StationsDAOTest {
    private StationsDAO stations = new StationsDAO();
    StationsMapper mapper = new StationsMapper();
    private static Long id;

    @Test
    void createStat() {
        StationsRqDTO dto = new StationsRqDTO("Университет", "Москва");
        Stations entity = mapper.toStations(dto);
        Assert.assertNotNull(entity);
        // проверка создания
        Assert.assertTrue(stations.create(entity));
        StationsRsDTO check_dto = mapper.toDto(entity);
        id = check_dto.getId();
        // проверка атрибутов
        Assert.assertNotNull(id);
        Assert.assertEquals(check_dto.getName(), "Университет");
        Assert.assertEquals(check_dto.getCity(), "Москва");
    }

    @Test
    void deleteStat() {
        Stations entity = stations.getEntityById(id, Stations.class);
        Assert.assertNotNull(entity);
        StationsRsDTO check_dto = mapper.toDto(entity);
        // проверим что достали нужную сущность по айди
        Assert.assertEquals(check_dto.getId(), id);
        stations.delete(entity);
        // проверка удаления
        Assert.assertNull(stations.getEntityById(id, Stations.class));
    }

    @Test
    void updateStat() {
        Stations old_entity = stations.getEntityById(1L, Stations.class);
        // если таблица пустая то создаем пустую запись и обновляем ее
        if (old_entity == null) {
            StationsRqDTO dto = new StationsRqDTO("Университет", "Москва");
            old_entity = mapper.toStations(dto);
            Assert.assertTrue(stations.create(old_entity));
        }
        StationsRsDTO dto = new StationsRsDTO(1L,"Парк культуры и отдыха","Пенза");
        Stations entity = mapper.toStations(dto);
        Assert.assertNotNull(entity);
        stations.update(entity);
        StationsRsDTO check_dto = mapper.toDto(entity);
        // проверка атрибутов
        Assert.assertEquals(check_dto.getId(), Long.valueOf(1L));
        Assert.assertEquals(check_dto.getName(), "Парк культуры и отдыха");
        Assert.assertEquals(check_dto.getCity(), "Пенза");
        stations.update(old_entity);
    }

    @Test
    public void getAllStats() {
        List<Stations> all = stations.getAll(Stations.class);
        Assert.assertNotNull(all);
    }
}
