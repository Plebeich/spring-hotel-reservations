package com.example.springexample.repository;


import com.example.springexample.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUserId(Integer userId);
    List<Booking> findByRoomId(Integer roomId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "WHERE b.room.id = :roomId " +
            "AND (:checkInDate < b.checkOutDate AND :checkOutDate > b.checkInDate)")
    boolean existsOverlappingBooking(
            @Param("roomId") Integer roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.room.id = :roomId " +
            "AND (:checkInDate < b.checkOutDate AND :checkOutDate > b.checkInDate)")
    List<Booking> findOverlappingBookings(
            @Param("roomId") Integer roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate);

    @Query("SELECT b FROM Booking b WHERE b.checkOutDate >= :today")
    List<Booking> findActiveBookings(@Param("today") LocalDate today);
}
