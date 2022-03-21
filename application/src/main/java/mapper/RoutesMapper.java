package mapper;

import org.modelmapper.ModelMapper;
import DTO.*;
import entities.*;
import java.util.*;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class RoutesMapper {
    public Routes toRoutes(RoutesRqDTO dto) {
        var modelMapper = new ModelMapper();
        TypeMap<RoutesRqDTO, Routes> typeMap = modelMapper.createTypeMap(RoutesRqDTO.class, Routes.class);
        typeMap.addMappings(
                mapper -> mapper.map(RoutesRqDTO::getCompany, Routes::setCompany));
        return modelMapper.map(dto, Routes.class);
    }

    public Routes toRoutes(RoutesRsDTO dto) {
        var modelMapper = new ModelMapper();
        TypeMap<RoutesRsDTO, Routes> typeMap = modelMapper.createTypeMap(RoutesRsDTO.class, Routes.class);
        typeMap.addMappings(
                mapper -> mapper.map(RoutesRsDTO::getCompany, Routes::setCompany));
        return modelMapper.map(dto, Routes.class);
    }

    public RoutesRsDTO toDto(Routes route) {
        var modelMapper = new ModelMapper();
        TypeMap<Routes, RoutesRsDTO> typeMap = modelMapper.createTypeMap(Routes.class, RoutesRsDTO.class);
        typeMap.addMappings(
                mapper -> mapper.map(Routes::getCompany, RoutesRsDTO::setCompany));
        return modelMapper.map(route, RoutesRsDTO.class);
    }
}