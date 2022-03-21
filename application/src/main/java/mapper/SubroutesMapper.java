package mapper;

import org.modelmapper.ModelMapper;
import DTO.*;
import entities.*;
import java.util.*;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class SubroutesMapper {
    public Subroutes toSubroutes(SubroutesRqDTO dto) {
        var modelMapper = new ModelMapper();
        TypeMap<SubroutesRqDTO, Subroutes> typeMap = modelMapper.createTypeMap(SubroutesRqDTO.class, Subroutes.class);
        typeMap.addMappings(
                mapper -> mapper.map(SubroutesRqDTO::getRoute, Subroutes::setRoute));
        typeMap.addMappings(
                mapper -> mapper.map(SubroutesRqDTO::getArrival, Subroutes::setArrival_st));
        typeMap.addMappings(
                mapper -> mapper.map(SubroutesRqDTO::getDepart, Subroutes::setDepart_st));
        return modelMapper.map(dto, Subroutes.class);
    }

    public Subroutes toSubroutes(SubroutesRsDTO dto) {
        var modelMapper = new ModelMapper();
        TypeMap<SubroutesRsDTO, Subroutes> typeMap = modelMapper.createTypeMap(SubroutesRsDTO.class, Subroutes.class);
        typeMap.addMappings(
                mapper -> mapper.map(SubroutesRsDTO::getRoute, Subroutes::setRoute));
        typeMap.addMappings(
                mapper -> mapper.map(SubroutesRsDTO::getArrival, Subroutes::setArrival_st));
        typeMap.addMappings(
                mapper -> mapper.map(SubroutesRsDTO::getDepart, Subroutes::setDepart_st));
        return modelMapper.map(dto, Subroutes.class);
    }

    public SubroutesRsDTO toDto(Subroutes route) {
        var modelMapper = new ModelMapper();
        TypeMap<Subroutes, SubroutesRsDTO> typeMap = modelMapper.createTypeMap(Subroutes.class, SubroutesRsDTO.class);
        typeMap.addMappings(
                mapper -> mapper.map(Subroutes::getRoute, SubroutesRsDTO::setRoute));
        typeMap.addMappings(
                mapper -> mapper.map(Subroutes::getArrival_st, SubroutesRsDTO::setArrival));
        typeMap.addMappings(
                mapper -> mapper.map(Subroutes::getDepart_st, SubroutesRsDTO::setDepart));
        return modelMapper.map(route, SubroutesRsDTO.class);
    }
}
