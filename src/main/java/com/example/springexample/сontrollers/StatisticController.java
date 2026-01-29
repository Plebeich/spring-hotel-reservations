package com.example.springexample.сontrollers;


import com.example.springexample.services.StatisticExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticExportService statisticExportService;

    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> exportStatistics() {
        return statisticExportService.exportStatisticsToCsv();
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getStatisticsCount() {
        long count = statisticExportService.getStatisticsCount();
        return ResponseEntity.ok(count);
    }
}
