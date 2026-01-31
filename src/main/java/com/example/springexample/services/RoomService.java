package com.example.springexample.services;

import com.example.springexample.config.RoomSpecification;
import com.example.springexample.dto.PageResponse;
import com.example.springexample.dto.RoomFilter;
import com.example.springexample.dto.RoomRequest;
import com.example.springexample.dto.RoomResponse;
import com.example.springexample.exeption.BadRequestException;
import com.example.springexample.exeption.CastomException;
import com.example.springexample.mapper.RoomMapper;
import com.example.springexample.model.ModelHotel;
import com.example.springexample.model.Room;
import com.example.springexample.repository.HotelRepository;
import com.example.springexample.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    @Transactional
    public RoomResponse createRoom(RoomRequest request){
        System.out.println("Создание комнаты: " + request.getName() + "в отеле: " + request.getHotelId());
        ModelHotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(() -> new CastomException("hotel", "id", request.getHotelId()));
        if (roomRepository.existsByNumberAndHotelId(request.getNumber(), request.getHotelId())){
            throw new BadRequestException("Номер" + request.getNumber() + " уже существует");
        }
        Room room = roomMapper.toEntity(request);
        room.setHotel(hotel);
        Room savedRoom = roomRepository.save(room);

        return roomMapper.toResponseDTO(savedRoom);
    }

    public RoomResponse getRoomById(int id){
        System.out.println("Поиск комнаты по ID: " + id);
        Room room = roomRepository.findById(id).orElseThrow(() -> new CastomException("Room", "id", id));
        return roomMapper.toResponseDTO(room);
    }

    public List<RoomResponse> getAllRooms(){
        System.out.println("Получение всех комнат");
        return roomRepository.findAll().stream().map(roomMapper::toResponseDTO).collect(Collectors.toList());
    }

    public List<RoomResponse> bookRooms(int hotelId){
        System.out.println("Получить номер по отелю: " + hotelId);
        return roomRepository.findByHotelId(hotelId).stream().map(roomMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public RoomResponse updateRoom(int id, RoomRequest request){
        System.out.println("Обновление комнаты по ID: " + id);
        Room room = roomRepository.findById(id).orElseThrow(() -> new CastomException("Room", "id", id));

        roomMapper.updateEntity(request,room);
        Room updatedRoom = roomRepository.save(room);
        return roomMapper.toResponseDTO(updatedRoom);
    }

    @Transactional
    public void deleteRoom(int id){
        System.out.println("удаление комнаты с ID: " + id);
        if (!roomRepository.existsById(id)){
            throw new CastomException("Room", "id", id);
        }
        roomRepository.deleteById(id);
        System.out.println("комната " + id + " удалена");
    }

    public PageResponse<RoomResponse> searchRooms(RoomFilter filter, int page, int size) {
        System.out.println("поиск комнаты по фильтру: " + filter + " " + page + " " +size);
        Specification<Room> spec = RoomSpecification.withFilter(filter);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Room> roomPage = roomRepository.findAll(spec, pageable);

        List<RoomResponse> content = roomPage.getContent().stream()
                .map(roomMapper::toResponseDTO)
                .collect(Collectors.toList());

        return PageResponse.<RoomResponse>builder()
                .content(content)
                .pageNumber(roomPage.getNumber())
                .pageSize(roomPage.getSize())
                .totalElements(roomPage.getTotalElements())
                .totalPages(roomPage.getTotalPages())
                .first(roomPage.isFirst())
                .last(roomPage.isLast())
                .build();
    }


}
