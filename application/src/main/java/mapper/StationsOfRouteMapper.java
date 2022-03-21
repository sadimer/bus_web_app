package mapper;

import org.modelmapper.ModelMapper;
import DTO.*;
import entities.*;
import java.util.*;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class StationsOfRouteMapper {
    public StationsOfRoute toStationsOfRoute(StationsOfRouteRqDTO dto) {
        var modelMapper = new ModelMapper();
        TypeMap<StationsOfRouteRqDTO, StationsOfRoute> typeMap = modelMapper.createTypeMap(
                StationsOfRouteRqDTO.class, StationsOfRoute.class);
        typeMap.addMappings(
                mapper -> mapper.map(StationsOfRouteRqDTO::getRoute, StationsOfRoute::setRoute));
        typeMap.addMappings(
                mapper -> mapper.map(StationsOfRouteRqDTO::getStation, StationsOfRoute::setSt));
        return modelMapper.map(dto, StationsOfRoute.class);
    }

    public StationsOfRoute toStationsOfRoute(StationsOfRouteRsDTO dto) {
        var modelMapper = new ModelMapper();
        TypeMap<StationsOfRouteRsDTO, StationsOfRoute> typeMap = modelMapper.createTypeMap(
                StationsOfRouteRsDTO.class, StationsOfRoute.class);
        typeMap.addMappings(
                mapper -> mapper.map(StationsOfRouteRsDTO::getRoute, StationsOfRoute::setRoute));
        typeMap.addMappings(
                mapper -> mapper.map(StationsOfRouteRsDTO::getStation, StationsOfRoute::setSt));
        return modelMapper.map(dto, StationsOfRoute.class);
    }

    public StationsOfRouteRsDTO toDto(StationsOfRoute strt) {
        var modelMapper = new ModelMapper();
        TypeMap<StationsOfRoute, StationsOfRouteRsDTO> typeMap = modelMapper.createTypeMap(
                StationsOfRoute.class, StationsOfRouteRsDTO.class);
        typeMap.addMappings(
                mapper -> mapper.map(StationsOfRoute::getRoute, StationsOfRouteRsDTO::setRoute));
        typeMap.addMappings(
                mapper -> mapper.map(StationsOfRoute::getSt, StationsOfRouteRsDTO::setStation));
        return modelMapper.map(strt, StationsOfRouteRsDTO.class);
    }
}
