package com.farm.controller;

import com.farm.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FarmController {

    @Autowired
    private FarmService farmService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAllAttributes(farmService.getDashboardData("day"));
        return "dashboard";
    }

    @PostMapping("/api/sensor-data")
    @ResponseBody
    public ResponseEntity<?> saveSensorData(
            @RequestParam double temperature,
            @RequestParam double moisture) {
        farmService.saveSensorData(temperature, moisture);
        return ResponseEntity.ok().body("{\"status\":\"success\"}");
    }

    @PostMapping("/api/animal-detect")
    @ResponseBody
    public ResponseEntity<?> detectAnimal() {
        farmService.incrementAnimalCount();
        return ResponseEntity.ok().body("{\"status\":\"animal_detected\"}");
    }

    @GetMapping("/api/dashboard-data")
    @ResponseBody
    public ResponseEntity<?> getDashboardData(
            @RequestParam(defaultValue = "day") String period) {
        try {
            return ResponseEntity.ok(farmService.getDashboardData(period));
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
