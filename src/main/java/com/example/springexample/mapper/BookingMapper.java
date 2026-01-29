package com.example.springexample.mapper;

import com.example.springexample.dto.BookingRequest;
import com.example.springexample.dto.BookingResponse;
import com.example.springexample.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "roomName", source = "room.name")
    @Mapping(target = "roomNumber", source = "room.number")
    @Mapping(target = "hotelId", source = "room.hotel.id")
    @Mapping(target = "hotelName", source = "room.hotel.name")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "userEmail", source = "user.email")
    BookingResponse toResponseDTO(Booking booking);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Booking toEntity(BookingRequest bookingRequest);
}
