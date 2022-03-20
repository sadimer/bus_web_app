package DAO;
import DTO.UsersRqDTO;
import DTO.UsersRsDTO;
import com.google.common.collect.Lists;
import entities.Users;
import mapper.UsersMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.util.*;

class UsersDAOTest<id> {
    private UsersDAO users = new UsersDAO();
    UsersMapper mapper = new UsersMapper();

    @Test
    void createDeleteUser() {
        UsersRqDTO dto = new UsersRqDTO("Андрей Андреевич Андреев", "12345", "Test", false);
        Users entity = mapper.toUsers(dto);
        Assert.assertNotNull(entity);
        // проверка создания
        Assert.assertTrue(users.create(entity));
        UsersRsDTO check_dto = mapper.toDto(entity);
        Long id = check_dto.getId();
        // проверка атрибутов
        Assert.assertNotNull(id);
        Assert.assertEquals(check_dto.getName(), "Андрей Андреевич Андреев");
        Assert.assertEquals(check_dto.getLogin(), "Test");
        Assert.assertEquals(check_dto.getPasswd(), "12345");
        Assert.assertNull(check_dto.getPhone());
        Assert.assertNull(check_dto.getEmail());

        // удаление
        entity = users.getEntityById(id, Users.class);
        Assert.assertNotNull(entity);
        check_dto = mapper.toDto(entity);
        // проверим что достали нужную сущность по айди
        Assert.assertEquals(check_dto.getId(), id);
        users.delete(entity);
        Assert.assertNull(users.getEntityById(id, Users.class));
    }
}