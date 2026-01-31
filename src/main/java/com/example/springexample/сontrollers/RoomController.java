package com.example.springexample.сontrollers;


import com.example.springexample.dto.PageResponse;
import com.example.springexample.dto.RoomFilter;
import com.example.springexample.dto.RoomRequest;
import com.example.springexample.dto.RoomResponse;
import com.example.springexample.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse createRoom(@Valid @RequestBody RoomRequest request){
        return roomService.createRoom(request);
    }


    @GetMapping("/{id}")
    public RoomResponse getRoom(@PathVariable Integer id) {
        return roomService.getRoomById(id);
    }

    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/hotel/{hotelId}")
    public List<RoomResponse> getRoomsByHotel(@PathVariable Integer hotelId) {
        return roomService.bookRooms(hotelId);
    }

    @PutMapping("/{id}")
    public RoomResponse updateRoom(@PathVariable Integer id, @Valid @RequestBody RoomRequest request) {
        return roomService.updateRoom(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
    }

    @GetMapping("/search")
    public PageResponse<RoomResponse> searchRooms(
            @ModelAttribute RoomFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return roomService.searchRooms(filter, page, size);
    }

}
