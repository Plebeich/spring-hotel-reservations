package com.example.springexample.mapper;

import com.example.springexample.dto.UserRequest;
import com.example.springexample.dto.UserResponse;
import com.example.springexample.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequest userRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UserRequest userRequest, @org.mapstruct.MappingTarget User user);
}
