package DAO;
import DTO.*;
import com.google.common.collect.Lists;
import entities.Subroutes;
import entities.Tickets;
import entities.Users;
import mapper.SubroutesMapper;
import mapper.TicketsMapper;
import mapper.UsersMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TicketsDAOTest {
    private TicketsDAO tickets = new TicketsDAO();
    private SubroutesDAO subroute = new SubroutesDAO();
    private UsersDAO user = new UsersDAO();
    TicketsMapper mapper = new TicketsMapper();
    SubroutesMapper sub_mapper = new SubroutesMapper();
    UsersMapper user_mapper = new UsersMapper();
    private static Long id;

    @Test
    void createTick() {
        Subroutes sub = subroute.getEntityById(1L, Subroutes.class);
        SubroutesRsDTO sub_dto = sub_mapper.toDto(sub);
        Users usr = user.getEntityById(1L, Users.class);
        UsersRsDTO usr_dto = user_mapper.toDto(usr);
        TicketsRqDTO dto = new TicketsRqDTO(usr_dto, sub_dto, 1L, 100.0);
        Tickets entity = mapper.toTickets(dto);
        Assert.assertNotNull(entity);
        // проверка создания
        Assert.assertTrue(tickets.create(entity));
        TicketsRsDTO check_dto = mapper.toDto(entity);
        id = check_dto.getId();
        // проверка атрибутов
        Assert.assertNotNull(id);
        Assert.assertEquals(check_dto.getSeats(), Long.valueOf(1L));
        Assert.assertEquals(check_dto.getUser().getId(), Long.valueOf(1L));
        Assert.assertEquals(check_dto.getSub().getId(), Long.valueOf(1L));
        Assert.assertEquals(check_dto.getPrice(), Double.valueOf(100L));
    }

    @Test
    void deleteTick() {
        Tickets entity = tickets.getEntityById(id, Tickets.class);
        Assert.assertNotNull(entity);
        TicketsRsDTO check_dto = mapper.toDto(entity);
        // проверим что достали нужную сущность по айди
        Assert.assertEquals(check_dto.getId(), id);
        tickets.delete(entity);
        // проверка удаления
        Assert.assertNull(tickets.getEntityById(id, Tickets.class));
    }

    @Test
    public void getAllTick() {
        List<Tickets> all = tickets.getAll(Tickets.class);
        Assert.assertNotNull(all);
    }
}
