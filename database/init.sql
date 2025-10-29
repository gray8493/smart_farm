-- Create database
CREATE DATABASE IF NOT EXISTS farm_management;

-- Use the database
USE farm_management;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS sensor_data;

-- Create sensor_data table
CREATE TABLE sensor_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    temperature DOUBLE NOT NULL,
    soil_moisture DOUBLE NOT NULL,
    animal_count INT NOT NULL DEFAULT 0,
    timestamp DATETIME NOT NULL,
    INDEX idx_timestamp (timestamp)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insert sample data (optional)
INSERT INTO sensor_data (temperature, soil_moisture, animal_count, timestamp) VALUES
(25.5, 60.2, 2, NOW() - INTERVAL 7 DAY),
(26.1, 58.7, 3, NOW() - INTERVAL 6 DAY),
(24.8, 62.3, 1, NOW() - INTERVAL 5 DAY),
(25.9, 59.8, 4, NOW() - INTERVAL 4 DAY),
(26.5, 57.5, 2, NOW() - INTERVAL 3 DAY),
(24.3, 63.1, 3, NOW() - INTERVAL 2 DAY),
(25.7, 60.8, 1, NOW() - INTERVAL 1 DAY),
(26.2, 59.5, 2, NOW());

-- Create a user for the application (change 'your_password' to a secure password)


-- Grant necessary permissions

