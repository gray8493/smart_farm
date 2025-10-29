package com.farm.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sensor_data")
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double temperature; // in Celsius

    @Column(nullable = false, name = "soil_moisture")
    private Double soilMoisture; // percentage

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer animalCount; // đếm số con vật

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
        if (animalCount == null) {
            animalCount = 0;
        }
    }
}
