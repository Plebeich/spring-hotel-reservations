package com.example.springexample.services;


import com.example.springexample.dto.BookingRequest;
import com.example.springexample.dto.BookingResponse;

import com.example.springexample.exeption.BadRequestException;
import com.example.springexample.exeption.CastomException;
import com.example.springexample.mapper.BookingMapper;
import com.example.springexample.model.Booking;
import com.example.springexample.model.Room;
import com.example.springexample.model.User;
import com.example.springexample.repository.BookingRepository;
import com.example.springexample.repository.RoomRepository;
import com.example.springexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final StatisticService statisticService;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        System.out.println("Создание бронирования для номера ");
        if (!request.isValid()) {
            throw new BadRequestException("Не корректная дата заселение");
        }

        if (request.getCheckInDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Не корректная дата заселение");
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new CastomException("Room", "id", request.getRoomId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CastomException("User", "id", request.getUserId()));

        if (bookingRepository.existsOverlappingBooking(
                request.getRoomId(),
                request.getCheckInDate(),
                request.getCheckOutDate())) {
            throw new BadRequestException("Номер на указанные даты занят");
        }

        Booking booking = bookingMapper.toEntity(request);
        booking.setRoom(room);
        booking.setUser(user);

        Booking savedBooking = bookingRepository.save(booking);

        statisticService.saveRoomBooking(
                user.getId(),
                room.getId(),
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        System.out.println("Номер" + savedBooking.getId() + "забронирован ");
        return bookingMapper.toResponseDTO(savedBooking);
    }

    public BookingResponse getBookingById(Integer id) {
        System.out.println("бронь по ID " + id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new CastomException("Booking", "id", id));
        return bookingMapper.toResponseDTO(booking);
    }

    public List<BookingResponse> getAllBookings() {
        System.out.println("Все бронирования");
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getBookingsByUserId(Integer userId) {
        System.out.println("Бронь по номеру");
        return bookingRepository.findByUserId(userId).stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getBookingsByRoomId(Integer roomId) {
        log.info("Getting bookings for room ID: {}", roomId);
        return bookingRepository.findByRoomId(roomId).stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getActiveBookings() {
        System.out.println("все активные бронирования");
        return bookingRepository.findActiveBookings(LocalDate.now()).stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public boolean isRoomAvailable(Integer roomId, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            return false;
        }
        return !bookingRepository.existsOverlappingBooking(roomId, checkIn, checkOut);
    }

    @Transactional
    public BookingResponse updateBooking(Integer id, BookingRequest request) {
        System.out.println("Изменение бронирования");

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new CastomException("Booking", "id", id));

        if (!request.isValid()) {
            throw new BadRequestException("Не корректная дата заселение");
        }

        if (booking.getRoom().getId() != request.getRoomId()) {
            Room room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new CastomException("Room", "id", request.getRoomId()));
            booking.setRoom(room);
        }

        if (booking.getUser().getId() != request.getUserId()) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new CastomException("User", "id", request.getUserId()));
            booking.setUser(user);
        }

        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());

        Booking updatedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponseDTO(updatedBooking);
    }

    @Transactional
    public void deleteBooking(Integer id) {
        System.out.println("удаление бронирования ");
        if (!bookingRepository.existsById(id)) {
            throw new CastomException("Booking", "id", id);
        }
        bookingRepository.deleteById(id);
    }
}
