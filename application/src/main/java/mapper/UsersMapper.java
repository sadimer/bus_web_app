package mapper;

import org.modelmapper.ModelMapper;
import DTO.*;
import entities.*;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {
    public Users toUsers(UsersRqDTO dto) {
        var mapper = new ModelMapper();
        return mapper.map(dto, Users.class);
    }

    public Users toUsers(UsersRqEmailDTO dto) {
        var mapper = new ModelMapper();
        return mapper.map(dto, Users.class);
    }

    public Users toUsers(UsersRqEmailPhoneDTO dto) {
        var mapper = new ModelMapper();
        return mapper.map(dto, Users.class);
    }

    public Users toUsers(UsersRqPhoneDTO dto) {
        var mapper = new ModelMapper();
        return mapper.map(dto, Users.class);
    }

    public UsersRsDTO toDto(Users user) {
        var mapper = new ModelMapper();
        return mapper.map(user, UsersRsDTO.class);
    }
}
