package com.example.fitnessspring.services;

import com.example.fitnessspring.models.entities.ActivityLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityLogService {


    public ActivityLog addActivityLog(ActivityLog activityLog);

    public List<ActivityLog> getAllActivitiesByUserId(Integer userId);

    public void deleteActivityLog(Integer id) ;
    public ActivityLog updateActivityLog(Integer id, ActivityLog activityLog);

}
