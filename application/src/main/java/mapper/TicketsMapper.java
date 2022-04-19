package mapper;

import DTO.TicketsRqDTO;
import DTO.TicketsRsDTO;
import entities.Tickets;
import org.modelmapper.ModelMapper;

import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class TicketsMapper {
    public Tickets toTickets(TicketsRqDTO dto) {
        var modelMapper = new ModelMapper();
        TypeMap<TicketsRqDTO, Tickets> typeMap = modelMapper.createTypeMap(TicketsRqDTO.class, Tickets.class);
        typeMap.addMappings(
                mapper -> mapper.map(TicketsRqDTO::getUser, Tickets::setUser));
        typeMap.addMappings(
                mapper -> mapper.map(TicketsRqDTO::getSub, Tickets::setSub));
        return modelMapper.map(dto, Tickets.class);
    }

    public Tickets toTickets(TicketsRsDTO dto) {
        var modelMapper = new ModelMapper();
        TypeMap<TicketsRsDTO, Tickets> typeMap = modelMapper.createTypeMap(TicketsRsDTO.class, Tickets.class);
        typeMap.addMappings(
                mapper -> mapper.map(TicketsRsDTO::getUser, Tickets::setUser));
        typeMap.addMappings(
                mapper -> mapper.map(TicketsRsDTO::getSub, Tickets::setSub));
        return modelMapper.map(dto, Tickets.class);
    }

    public TicketsRsDTO toDto(Tickets ticket) {
        var modelMapper = new ModelMapper();
        TypeMap<Tickets, TicketsRsDTO> typeMap = modelMapper.createTypeMap(Tickets.class, TicketsRsDTO.class);
        typeMap.addMappings(
                mapper -> mapper.map(Tickets::getUser, TicketsRsDTO::setUser));
        typeMap.addMappings(
                mapper -> mapper.map(Tickets::getSub, TicketsRsDTO::setSub));
        return modelMapper.map(ticket, TicketsRsDTO.class);
    }
}
