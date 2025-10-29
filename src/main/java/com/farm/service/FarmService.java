package com.farm.service;

import com.farm.model.SensorData;
import com.farm.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FarmService {

    @Autowired
    private SensorDataRepository sensorDataRepository;
    
    public SensorData getLatestSensorData() {
        return sensorDataRepository.findTopByOrderByTimestampDesc();
    }
    
    public void saveSensorData(double temperature, double soilMoisture) {
        SensorData data = new SensorData();
        data.setTemperature(temperature);
        data.setSoilMoisture(soilMoisture);
        data.setAnimalCount(0); // mặc định là 0
        sensorDataRepository.save(data);
    }
    
    public Map<String, Object> getDashboardData(String period) {
        Map<String, Object> dashboardData = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate;
        
        // Xác định khoảng thời gian dựa trên period
        switch (period.toLowerCase()) {
            case "week":
                startDate = now.minusWeeks(1);
                break;
            case "month":
                startDate = now.minusMonths(1);
                break;
            case "day":
            default:
                startDate = now.minusDays(1);
                break;
        }
        
        // Lấy dữ liệu từ database
        List<SensorData> sensorData = sensorDataRepository
            .findByTimestampBetween(startDate, now);

        // Debug: In ra số lượng bản ghi
        System.out.println("Found " + sensorData.size() + " records for period: " + period);
        
        // Lấy dữ liệu mới nhất
        if (!sensorData.isEmpty()) {
            SensorData latest = sensorData.get(sensorData.size() - 1);
            dashboardData.put("currentTemperature", latest.getTemperature());
            dashboardData.put("currentMoisture", latest.getSoilMoisture());
            dashboardData.put("currentAnimalCount", latest.getAnimalCount());
            dashboardData.put("lastUpdated", latest.getTimestamp());
        } else {
            // Trả về giá trị mặc định nếu không có dữ liệu
            dashboardData.put("currentTemperature", 0);
            dashboardData.put("currentMoisture", 0);
            dashboardData.put("currentAnimalCount", 0);
            dashboardData.put("lastUpdated", LocalDateTime.now());
        }
        
        // Chuẩn bị dữ liệu cho bảng - lấy tối đa 10 bản ghi gần nhất để tránh quá tải
        List<SensorData> tableData = sensorData.size() > 10 ?
            sensorData.subList(sensorData.size() - 10, sensorData.size()) : sensorData;
        dashboardData.put("tableData", tableData);
        
        return dashboardData;
    }
    
    private Map<String, Object> prepareChartData(List<SensorData> data, String period) {
        Map<String, Object> result = new HashMap<>();
        
        if (data.isEmpty()) {
            result.put("labels", new String[0]);
            result.put("temperatures", new double[0]);
            result.put("moisture", new double[0]);
            return result;
        }
        
        // Nhóm dữ liệu theo khoảng thời gian
        Map<LocalDateTime, List<SensorData>> groupedData = data.stream()
            .collect(Collectors.groupingBy(sd -> {
                LocalDateTime timestamp = sd.getTimestamp();
                switch (period.toLowerCase()) {
                    case "week":
                        return timestamp.truncatedTo(ChronoUnit.DAYS);
                    case "month":
                        return timestamp.truncatedTo(ChronoUnit.DAYS);
                    case "day":
                    default:
                        return timestamp.truncatedTo(ChronoUnit.HOURS);
                }
            }));
        
        // Sắp xếp các nhóm theo thời gian
        List<LocalDateTime> sortedKeys = new ArrayList<>(groupedData.keySet());
        Collections.sort(sortedKeys);
        
        // Tạo mảng dữ liệu cho biểu đồ
        String[] labels = new String[sortedKeys.size()];
        double[] temperatures = new double[sortedKeys.size()];
        double[] moisture = new double[sortedKeys.size()];
        
        for (int i = 0; i < sortedKeys.size(); i++) {
            LocalDateTime key = sortedKeys.get(i);
            List<SensorData> group = groupedData.get(key);
            
            // Tính giá trị trung bình cho mỗi nhóm
            double avgTemp = group.stream()
                .mapToDouble(SensorData::getTemperature)
                .average()
                .orElse(0);
                
            double avgMoisture = group.stream()
                .mapToDouble(SensorData::getSoilMoisture)
                .average()
                .orElse(0);
                
            // Định dạng nhãn thời gian
            String label;
            switch (period.toLowerCase()) {
                case "week":
                    label = key.getDayOfWeek().toString().substring(0, 3) + " " + key.getDayOfMonth();
                    break;
                case "month":
                    label = key.getDayOfMonth() + "/" + key.getMonthValue();
                    break;
                case "day":
                default:
                    label = String.format("%02d:00", key.getHour());
                    break;
            }
            
            labels[i] = label;
            temperatures[i] = Math.round(avgTemp * 10) / 10.0; // Làm tròn 1 chữ số thập phân
            moisture[i] = Math.round(avgMoisture * 10) / 10.0;
        }
        
        result.put("labels", labels);
        result.put("temperatures", temperatures);
        result.put("moisture", moisture);
        
        return result;
    }
    
    public Map<String, Object> getWeatherForecast() {
        // Placeholder for weather data
        Map<String, Object> forecast = new HashMap<>();
        forecast.put("status", "Dịch vụ thời tiết chưa được cấu hình");
        return forecast;
    }

    public void incrementAnimalCount() {
        // Tạo bản ghi mới chỉ để cập nhật đếm số con vật
        SensorData data = new SensorData();
        data.setTemperature(0.0); // giá trị mặc định
        data.setSoilMoisture(0.0); // giá trị mặc định

        // Lấy bản ghi mới nhất để tăng số đếm
        SensorData latest = sensorDataRepository.findTopByOrderByTimestampDesc();
        int currentCount = (latest != null) ? latest.getAnimalCount() : 0;
        data.setAnimalCount(currentCount + 1);

        sensorDataRepository.save(data);
    }
}
