package com.example.springexample.repository;

import com.example.springexample.model.ModelHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<ModelHotel, Integer>, JpaSpecificationExecutor<ModelHotel> {
}
