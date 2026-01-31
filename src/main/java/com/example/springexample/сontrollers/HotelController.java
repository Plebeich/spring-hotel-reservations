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
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponse createHotel(@RequestBody HotelRequest request){
        return hotelService.createHotel(request);
    }

    @GetMapping("/{id}")
    public HotelResponse getHotel(@PathVariable int id){
        return hotelService.getHotelById(id);
    }

    @GetMapping()
    public List<HotelResponse> getAllHotels(){
        return hotelService.getAllHotels();
    }

    @PutMapping("/{id}")
    public HotelResponse updateHotel(@PathVariable int id,@Valid @RequestBody HotelRequest request){
        return hotelService.updateHotel(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable int id){
        hotelService.deleteHotel(id);
    }

    @PostMapping("/{id}/rate")
    public HotelResponse rateHotel(@PathVariable Integer id, @Valid @RequestBody RatingRequest ratingRequest) {
        return hotelService.rateHotel(id, ratingRequest.getRating());
    }

    @GetMapping("/{id}/rating")
    public HotelRatingResponse getHotelRating(@PathVariable Integer id) {
        return hotelService.getHotelRatingInfo(id);
    }

    @GetMapping("/search")
    public PageResponse<HotelResponse> searchHotels(
            @ModelAttribute HotelFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return hotelService.searchHotels(filter,page,size);
    }


}
