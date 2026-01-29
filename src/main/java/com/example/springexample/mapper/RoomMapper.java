package com.example.springexample.mapper;

import com.example.springexample.dto.RoomRequest;
import com.example.springexample.dto.RoomResponse;
import com.example.springexample.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(target = "hotelId", source = "hotel.id")
    @Mapping(target = "hotelName", source = "hotel.name")
    RoomResponse toResponseDTO(Room room);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    Room toEntity(RoomRequest roomRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    void updateEntity(RoomRequest roomRequest, @MappingTarget Room room);
}
