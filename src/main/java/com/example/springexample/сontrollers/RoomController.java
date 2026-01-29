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
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest request){
        RoomResponse response = roomService.createRoom(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Integer id) {
        RoomResponse response = roomService.getRoomById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomResponse>> getRoomsByHotel(@PathVariable Integer hotelId) {
        List<RoomResponse> rooms = roomService.bookRooms(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Integer id, @Valid @RequestBody RoomRequest request) {
        RoomResponse response = roomService.updateRoom(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<RoomResponse>> searchRooms(
            @ModelAttribute RoomFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<RoomResponse> response = roomService.searchRooms(filter, page, size);
        return ResponseEntity.ok(response);
    }

}
