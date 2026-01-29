package com.example.springexample.repository;

import com.example.springexample.model.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, Long> {
}
