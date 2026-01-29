package com.example.springexample.repository;

import com.example.springexample.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room> {
    List<Room> findByHotelId(int hotelId);
    boolean existsByNumberAndHotelId(String number, int hotelId);
    boolean existsById(Integer id);
}
