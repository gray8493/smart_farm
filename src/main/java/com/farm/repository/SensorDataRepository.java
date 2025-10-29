package com.farm.repository;

import com.farm.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    
    // Find the latest sensor data
    SensorData findTopByOrderByTimestampDesc();
    
    // Find sensor data within a specific time range
    @Query("SELECT s FROM SensorData s WHERE s.timestamp >= :startDate AND s.timestamp <= :endDate ORDER BY s.timestamp ASC")
    List<SensorData> findByTimestampBetween(@Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);
    
    // Find average temperature and moisture for the last 7 days, grouped by day
    @Query(value = "SELECT DATE(timestamp) as day, " +
            "AVG(temperature) as avgTemp, " +
            "AVG(soil_moisture) as avgMoisture " +
            "FROM sensor_data " +
            "WHERE timestamp >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "GROUP BY DATE(timestamp) " +
            "ORDER BY day ASC", nativeQuery = true)
    List<Object[]> findWeeklyAverages();
}
