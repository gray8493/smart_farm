-- Clear existing data
TRUNCATE TABLE sensor_data;

-- Add realistic sample data for the last 30 days with hourly readings
INSERT INTO sensor_data (temperature, soil_moisture, timestamp)
WITH RECURSIVE dates AS (
    -- Start from 30 days ago
    SELECT CURRENT_DATE - INTERVAL 30 DAY as dt, 0 as hour_offset
    UNION ALL
    SELECT dt + INTERVAL 1 DAY, 0 FROM dates WHERE dt < CURRENT_DATE - INTERVAL 1 DAY
),
hours AS (
    -- Generate hours for each day (from 0 to 23)
    SELECT dt, hour_offset FROM dates
    UNION ALL
    SELECT dt, hour_offset + 1 FROM hours WHERE hour_offset < 23
)
SELECT 
    -- Realistic temperature pattern (colder at night, warmer during the day)
    -- Base temperature varies by season (lower in winter, higher in summer)
    ROUND(
        15 + 
        (DAYOFYEAR(dt + INTERVAL hour_offset HOUR) - 1) / 365 * 15 +  -- Seasonal variation (15-30°C)
        (SIN((hour_offset - 12) * PI() / 12) * 8) +  -- Daily fluctuation (±8°C)
        (RAND() * 4 - 2),  -- Random variation (±2°C)
        1
    ) as temperature,
    
    -- Realistic soil moisture pattern (dries during the day, replenishes at night)
    -- Also includes some rain events
    ROUND(
        CASE 
            -- Simulate rain events (random 10% chance of rain)
            WHEN RAND() < 0.1 THEN 
                70 + (RAND() * 20)  -- Rain increases moisture
            ELSE
                -- Normal daily pattern (drier during the day)
                40 + 
                (SIN((hour_offset - 4) * PI() / 12) * -15) +  -- Daily pattern
                (RAND() * 10 - 5)  -- Random variation
        END,
        1
    ) as soil_moisture,
    
    -- Timestamp for each hour
    dt + INTERVAL hour_offset HOUR as timestamp
FROM hours
WHERE dt + INTERVAL hour_offset HOUR <= NOW()
ORDER BY timestamp;

-- Verify data was inserted
SELECT 
    COUNT(*) as total_records, 
    MIN(timestamp) as first_record,
    MAX(timestamp) as last_record,
    ROUND(AVG(temperature), 1) as avg_temp,
    ROUND(AVG(soil_moisture), 1) as avg_moisture,
    COUNT(CASE WHEN soil_moisture > 80 THEN 1 END) as rainy_periods
FROM sensor_data;
