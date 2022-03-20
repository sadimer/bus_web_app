import DAO.*;
import DTO.*;
import mapper.*;
import entities.Users;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        UsersDAO users = new UsersDAO();
        UsersMapper mapper = new UsersMapper();

        UsersRqDTO dto = new UsersRqDTO("Андрей Андреевич Андреев", "12345", "Test", false);
        Users entity = mapper.toUsers(dto);
        users.create(entity);
        UsersRsDTO check_dto = mapper.toDto(entity);
        System.out.println(check_dto.getId());
        System.out.println(check_dto.getName());
        System.out.println(check_dto.getLogin());
        System.out.println(check_dto.getPasswd());
        System.out.println(check_dto.getEmail());
        System.out.println(check_dto.getPhone());
    }
}