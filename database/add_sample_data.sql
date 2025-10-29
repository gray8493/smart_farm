-- Clear existing data
TRUNCATE TABLE sensor_data;

-- Add sample data for the last 7 days (168 hours)
INSERT INTO sensor_data (temperature, soil_moisture, timestamp)
WITH RECURSIVE hours AS (
    SELECT 0 as hour_offset
    UNION ALL
    SELECT hour_offset + 1 FROM hours WHERE hour_offset < 167  -- 7 days * 24 hours - 1
)
SELECT 
    -- Temperature: base 25°C with daily pattern and random variation
    ROUND(25 + 
          (SIN((HOUR(DATE_SUB(NOW(), INTERVAL hour_offset HOUR)) - 12) * PI() / 12) * 5) + 
          (RAND() * 4 - 2), 1) as temperature,
          
    -- Moisture: base 60% with inverse pattern and random variation
    ROUND(60 + 
          (SIN((HOUR(DATE_SUB(NOW(), INTERVAL hour_offset HOUR)) - 4) * PI() / 12) * -10) + 
          (RAND() * 6 - 3), 1) as soil_moisture,
          
    -- Timestamp: current time minus hour_offset hours
    DATE_SUB(NOW(), INTERVAL hour_offset HOUR) as timestamp
FROM hours
ORDER BY timestamp;

-- Verify data was inserted
SELECT 
    COUNT(*) as total_records, 
    MIN(timestamp) as first_record,
    MAX(timestamp) as last_record,
    ROUND(AVG(temperature), 1) as avg_temp,
    ROUND(AVG(soil_moisture), 1) as avg_moisture
FROM sensor_data;
