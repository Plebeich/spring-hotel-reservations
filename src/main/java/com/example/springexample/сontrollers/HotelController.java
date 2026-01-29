package com.example.springexample.сontrollers;

import com.example.springexample.dto.*;
import com.example.springexample.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path = "/api/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(@Valid @RequestBody HotelRequest request){
        HotelResponse response = hotelService.createHotel(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotel(@PathVariable int id){
        HotelResponse response = hotelService.getHotelById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<HotelResponse>> getAllHotels(){
        List<HotelResponse> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable int id,@Valid @RequestBody HotelRequest request){
        HotelResponse response = hotelService.updateHotel(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable int id){
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<HotelResponse> rateHotel(
            @PathVariable Integer id,
            @Valid @RequestBody RatingRequest ratingRequest) {
        HotelResponse response = hotelService.rateHotel(id, ratingRequest.getRating());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/rating")
    public ResponseEntity<Map<String, Object>> getHotelRating(@PathVariable Integer id) {
        Map<String, Object> ratingInfo = hotelService.getHotelRatingInfo(id);
        return ResponseEntity.ok(ratingInfo);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<HotelResponse>> searchHotels(
            @ModelAttribute HotelFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<HotelResponse> response = hotelService.searchHotels(filter, page, size);
        return ResponseEntity.ok(response);
    }


}
