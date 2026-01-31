package com.example.springexample.сontrollers;


import com.example.springexample.dto.BookingRequest;
import com.example.springexample.dto.BookingResponse;
import com.example.springexample.mapper.BookingMapper;
import com.example.springexample.repository.BookingRepository;
import com.example.springexample.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createBooking(@Valid @RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping("/{id}")
    public BookingResponse getBooking(@PathVariable Integer id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/user/{userId}")
    public List<BookingResponse> getBookingsByUser(@PathVariable Integer userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    @GetMapping("/room/{roomId}")
    public List<BookingResponse> getBookingsByRoom(@PathVariable Integer roomId) {
        return bookingService.getBookingsByRoomId(roomId);
    }

    @GetMapping("/active")
    public List<BookingResponse> getActiveBookings() {
        return bookingService.getActiveBookings();
    }

    @GetMapping("/availability")
    public boolean checkRoomAvailability(
            @RequestParam Integer roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        return bookingService.isRoomAvailable(roomId, checkIn, checkOut);
    }

    @PutMapping("/{id}")
    public BookingResponse updateBooking(@PathVariable Integer id, @Valid @RequestBody BookingRequest request) {
        return bookingService.updateBooking(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
    }
}
