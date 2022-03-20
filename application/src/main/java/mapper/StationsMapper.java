package mapper;

import org.modelmapper.ModelMapper;
import DTO.*;
import entities.*;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class StationsMapper {
    public Stations toStations(StationsRqDTO dto) {
        var mapper = new ModelMapper();
        return mapper.map(dto, Stations.class);
    }

    public StationsRsDTO toDto(Stations station) {
        var mapper = new ModelMapper();
        return mapper.map(station, StationsRsDTO.class);
    }
}
