package com.example.springexample.services;

import com.example.springexample.model.Statistic;
import com.example.springexample.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StatisticService {
    @Value("${app.statistics.enabled:true}")
    private boolean statisticsEnabled;

    private final StatisticRepository statisticRepository;

    public void saveUserRegistration(Integer userId) {
        if (!statisticsEnabled) {
            return;
        }

        Statistic statistic = new Statistic();
        statistic.setEventType("USER_REGISTERED");
        statistic.setUserId(userId);
        statistic.setAdditionalData("время регистрации пользователя"+ LocalDate.now());

        statisticRepository.save(statistic);

    }

    public void saveRoomBooking(Integer userId, Integer roomId,
                                LocalDate checkInDate, LocalDate checkOutDate) {
        if (!statisticsEnabled) {
            return;
        }

        Statistic statistic = new Statistic();
        statistic.setEventType("ROOM_BOOKED");
        statistic.setUserId(userId);
        statistic.setRoomId(roomId);
        statistic.setCheckInDate(checkInDate.toString());
        statistic.setCheckOutDate(checkOutDate.toString());
        statistic.setAdditionalData(String.format("Booking from %s to %s",
                checkInDate, checkOutDate));

        statisticRepository.save(statistic);
    }
}
