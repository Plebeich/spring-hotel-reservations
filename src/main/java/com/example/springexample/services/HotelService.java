package com.example.springexample.services;

import com.example.springexample.config.HotelSpecification;
import com.example.springexample.dto.*;
import com.example.springexample.exeption.BadRequestException;
import com.example.springexample.exeption.CastomException;
import com.example.springexample.mapper.HotelMapper;
import com.example.springexample.model.Hotel;
import com.example.springexample.repository.HotelRepository;
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
@Transactional(readOnly = true)
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Transactional
    public HotelResponse createHotel(HotelRequest request){
        Hotel hotel = hotelMapper.toEntity(request);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toResponseDTO(savedHotel);
    }

    public HotelResponse getHotelById(int id){  //otel po id
        System.out.println("Поиск отеля по ID - " + id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new CastomException("Hotel", "id", id));
        return hotelMapper.toResponseDTO(hotel);
    }

    public List<HotelResponse> getAllHotels(){ //vse oteli
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public HotelResponse updateHotel(int id, HotelRequest request){

        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new CastomException("Hotel", "id", id));
        hotelMapper.updateEntity(request,hotel);
        System.out.println("Отель " + id + " изменён");
        return hotelMapper.toResponseDTO(hotelRepository.save(hotel));
    }

    @Transactional
    public void deleteHotel(int id){
        if (hotelRepository.findById(id).isEmpty()){
            System.out.println("ID не найден");
            throw new CastomException("Hotel", "id", id);
        }
        hotelRepository.deleteById(id);
    }


    @Transactional
    public HotelResponse rateHotel(Integer hotelId, Integer newRating) {
        System.out.println("рейтинг отеля " + hotelId + " - " + newRating);

        if (newRating < 1 || newRating > 5) {
            throw new BadRequestException("Допустимый рейтинг отеля с 1 до 5");
        }

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new CastomException("Hotel", "id", hotelId));
        double currentRating = hotel.getRating();
        int currentReviewsCount = hotel.getReviewsCount();
        double totalRating = currentRating * currentReviewsCount;
        totalRating = totalRating - currentRating + newRating;
        int newReviewsCount = currentReviewsCount + 1;
        double newAverageRating = totalRating / newReviewsCount;

        newAverageRating = Math.round(newAverageRating * 10.0) / 10.0;
        hotel.setRating(newAverageRating);
        hotel.setReviewsCount(newReviewsCount);

        Hotel updatedHotel = hotelRepository.save(hotel);
        log.info("рейтинг отеля: " + hotelId + " изменён на - " + newAverageRating + " количество отзывов - " + newReviewsCount);

        return hotelMapper.toResponseDTO(updatedHotel);
    }

    public HotelRatingResponse getHotelRatingInfo(Integer hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new CastomException("Hotel", "id", hotelId));
        HotelRatingResponse response = new HotelRatingResponse();
        response.setHotelId(hotel.getId());
        response.setHotelName(hotel.getName());
        response.setCurrentRating(hotel.getRating());
        response.setNumberRatings(hotel.getReviewsCount());

        return response;
    }

    public PageResponse<HotelResponse> searchHotels(HotelFilter filter, int page, int size) {
        System.out.println("поиск отеля по фильтру: " + filter + " " + page + " " + size);
        Specification<Hotel> spec = HotelSpecification.withFilter(filter);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Hotel> hotelPage = hotelRepository.findAll(spec, pageable);

        List<HotelResponse> content = hotelPage.getContent().stream()
                .map(hotelMapper::toResponseDTO)
                .collect(Collectors.toList());

        return PageResponse.<HotelResponse>builder()
                .content(content)
                .pageNumber(hotelPage.getNumber())
                .pageSize(hotelPage.getSize())
                .totalElements(hotelPage.getTotalElements())
                .totalPages(hotelPage.getTotalPages())
                .first(hotelPage.isFirst())
                .last(hotelPage.isLast())
                .build();
    }
}
