package DAO;
import DTO.UsersRqDTO;
import DTO.UsersRqEmailPhoneDTO;
import DTO.UsersRsDTO;
import com.google.common.collect.Lists;
import entities.Routes;
import entities.Users;
import mapper.UsersMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UsersDAOTest {
    private UsersDAO users = new UsersDAO();
    UsersMapper mapper = new UsersMapper();
    private static Long id;

    @Test
    void createUser() {
        UsersRqDTO dto = new UsersRqDTO("Андрей Андреевич Андреев", "12345", "Test", false);
        Users entity = mapper.toUsers(dto);
        Assert.assertNotNull(entity);
        // проверка создания
        Assert.assertTrue(users.create(entity));
        UsersRsDTO check_dto = mapper.toDto(entity);
        id = check_dto.getId();
        // проверка атрибутов
        Assert.assertNotNull(id);
        Assert.assertEquals(check_dto.getName(), "Андрей Андреевич Андреев");
        Assert.assertEquals(check_dto.getLogin(), "Test");
        Assert.assertEquals(check_dto.getPasswd(), "12345");
        Assert.assertNull(check_dto.getPhone());
        Assert.assertNull(check_dto.getEmail());
    }

    @Test
    void deleteUser() {
        Users entity = users.getEntityById(id, Users.class);
        Assert.assertNotNull(entity);
        UsersRsDTO check_dto = mapper.toDto(entity);
        // проверим что достали нужную сущность по айди
        Assert.assertEquals(check_dto.getId(), id);
        users.delete(entity);
        // проверка удаления
        Assert.assertNull(users.getEntityById(id, Users.class));
    }

    @Test
    void updateUser() {
        Users old_entity = users.getEntityById(1L, Users.class);
        UsersRsDTO dto = new UsersRsDTO(1L,"Иван Иванович Иванов","test@mail.ru", "+7000000000", "12345", "Test", false);
        Users entity = mapper.toUsers(dto);
        Assert.assertNotNull(entity);
        users.update(entity);
        UsersRsDTO check_dto = mapper.toDto(entity);
        // проверка атрибутов
        Assert.assertEquals(check_dto.getId(), Long.valueOf(1L));
        Assert.assertEquals(check_dto.getName(), "Иван Иванович Иванов");
        Assert.assertEquals(check_dto.getLogin(), "Test");
        Assert.assertEquals(check_dto.getPasswd(), "12345");
        Assert.assertEquals(check_dto.getPhone(), "+7000000000");
        Assert.assertEquals(check_dto.getEmail(), "test@mail.ru");
        users.update(old_entity);
    }

    @Test
    public void getAllUsers() {
        List<Users> all = users.getAll(Users.class);
        Assert.assertNotNull(all);
    }

    @Test
    public void filterUsers() {
        List<Users> result = users.filter(Map.of("nameFilter",
                Lists.newArrayList("%Котова%")), Users.class);
        Assert.assertEquals(result.size(), 1);
        result.forEach(user -> Assert.assertTrue(user.getName().contains("Котова")));
        result = users.filter(Map.of("loginFilter",
                Lists.newArrayList("join4")), Users.class);
        Assert.assertEquals(result.size(), 1);
        result.forEach(user -> Assert.assertTrue(user.getLogin().contains("join4")));
    }
}