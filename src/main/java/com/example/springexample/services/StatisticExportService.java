package com.example.springexample.services;



import com.example.springexample.model.Statistic;

import com.example.springexample.repository.StatisticRepository;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticExportService {

    private final StatisticRepository statisticRepository;

    public ResponseEntity<Resource> exportStatisticsToCsv() {
        List<Statistic> events = statisticRepository.findAll();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            String[] header = {
                    "ID события",
                    "Тип события",
                    "ID пользователя",
                    "ID номера",
                    "Дата заезда",
                    "Дата выезда",
                    "Дополнительные данные",
                    "Дата создания"
            };
            writer.writeNext(header);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (Statistic event : events) {
                String[] row = new String[8];
                row[0] = event.getId().toString();
                row[1] = event.getEventType();
                row[2] = event.getUserId() != null ? event.getUserId().toString() : "";
                row[3] = event.getRoomId() != null ? event.getRoomId().toString() : "";
                row[4] = event.getCheckInDate() != null ? event.getCheckInDate() : "";
                row[5] = event.getCheckOutDate() != null ? event.getCheckOutDate() : "";
                row[6] = event.getAdditionalData() != null ? event.getAdditionalData() : "";
                row[7] = event.getCreatedAt() != null ? event.getCreatedAt().format(formatter) : "";

                writer.writeNext(row);
            }

            writer.flush();

        } catch (Exception e) {
            throw new RuntimeException("не удалось сгенерировать CSV", e);
        }

        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=hotel_statistics_" +
                        java.time.LocalDate.now() + ".csv")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    public long getStatisticsCount() {
        return statisticRepository.count();
    }
}
