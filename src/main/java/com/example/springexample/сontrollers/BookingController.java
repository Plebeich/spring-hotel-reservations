package com.example.springexample.сontrollers;


import com.example.springexample.dto.BookingRequest;
import com.example.springexample.dto.BookingResponse;
import com.example.springexample.dto.PageResponse;
import com.example.springexample.mapper.BookingMapper;
import com.example.springexample.model.Booking;
import com.example.springexample.repository.BookingRepository;
import com.example.springexample.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Integer id) {
        BookingResponse response = bookingService.getBookingById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponse<BookingResponse>> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Booking> bookingPage = bookingRepository.findAll(pageable);

        List<BookingResponse> content = bookingPage.getContent().stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());

        PageResponse<BookingResponse> response = new PageResponse<>(
                content,
                bookingPage.getNumber(),
                bookingPage.getSize(),
                bookingPage.getTotalElements(),
                bookingPage.getTotalPages(),
                bookingPage.isFirst(),
                bookingPage.isLast()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@PathVariable Integer userId) {
        List<BookingResponse> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByRoom(@PathVariable Integer roomId) {
        List<BookingResponse> bookings = bookingService.getBookingsByRoomId(roomId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/active")
    public ResponseEntity<List<BookingResponse>> getActiveBookings() {
        List<BookingResponse> bookings = bookingService.getActiveBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkRoomAvailability(
            @RequestParam Integer roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        boolean isAvailable = bookingService.isRoomAvailable(roomId, checkIn, checkOut);
        return ResponseEntity.ok(isAvailable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(
            @PathVariable Integer id,
            @Valid @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.updateBooking(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
