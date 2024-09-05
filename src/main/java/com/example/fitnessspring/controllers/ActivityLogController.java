package com.example.fitnessspring.controllers;

import com.example.fitnessspring.models.entities.ActivityLog;
import com.example.fitnessspring.services.ActivityLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:4200")
public class ActivityLogController {
    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @PostMapping
    public ActivityLog addActivityLog(@RequestBody ActivityLog activityLog) {
        return activityLogService.addActivityLog(activityLog);
    }

    @GetMapping("/user/{userId}")
    public List<ActivityLog> getAllActivitiesByUserId(@PathVariable Integer userId) {
        return activityLogService.getAllActivitiesByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteActivityLog(@PathVariable Integer id) {
        activityLogService.deleteActivityLog(id);
    }
}
