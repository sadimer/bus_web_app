package mapper;

import DTO.StationsRqDTO;
import DTO.StationsRsDTO;
import entities.Stations;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StationsMapper {
    public Stations toStations(StationsRqDTO dto) {
        var mapper = new ModelMapper();
        return mapper.map(dto, Stations.class);
    }

    public Stations toStations(StationsRsDTO dto) {
        var mapper = new ModelMapper();
        return mapper.map(dto, Stations.class);
    }

    public StationsRsDTO toDto(Stations station) {
        var mapper = new ModelMapper();
        return mapper.map(station, StationsRsDTO.class);
    }
}
