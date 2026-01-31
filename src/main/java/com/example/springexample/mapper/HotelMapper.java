package com.example.springexample.mapper;

import com.example.springexample.dto.HotelRequest;
import com.example.springexample.dto.HotelResponse;
import com.example.springexample.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    HotelResponse toResponseDTO(Hotel hotel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "reviewsCount", ignore = true)
    Hotel toEntity(HotelRequest hotelRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "reviewsCount", ignore = true)
    void updateEntity(HotelRequest hotelRequest, @MappingTarget Hotel hotel);
}