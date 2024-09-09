package com.example.fitnessspring.services.impl;

import com.example.fitnessspring.enums.DifficultyLevelEnum;
import com.example.fitnessspring.models.entities.*;
import com.example.fitnessspring.repositories.ActivityLogRepository;
import com.example.fitnessspring.repositories.UserRepository;
import com.example.fitnessspring.services.ActivityLogService;
import com.example.fitnessspring.services.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {
    private final ActivityLogRepository activityLogRepository;
    private final LogService logService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public ActivityLogServiceImpl(ActivityLogRepository activityLogRepository, LogService logService, UserRepository userRepository, ModelMapper modelMapper) {
        this.activityLogRepository = activityLogRepository;
        this.logService = logService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ActivityLog addActivityLog(ActivityLog activityLog) {
        UserEntity userEntity = userRepository.findById(activityLog.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        ActivitylogEntity activitylogEntity = new ActivitylogEntity();
        activitylogEntity.setId(activityLog.getId());
        activitylogEntity.setExerciseType(activityLog.getExerciseType());
        activitylogEntity.setDuration(activityLog.getDuration());
        activitylogEntity.setDifficultyLevel(DifficultyLevelEnum.valueOf(activityLog.getDifficultyLevel()));
        activitylogEntity.setResult(activityLog.getResult());
        activitylogEntity.setUser(userEntity);


       activitylogEntity =  activityLogRepository.save(activitylogEntity);

        logService.log("INFO", "ActivityLog added: " + activitylogEntity.getId());

        return modelMapper.map(activitylogEntity, ActivityLog.class);
    }

    public List<ActivityLog> getAllActivitiesByUserId(Integer userId) {
        logService.log("INFO", "Fetched activities for user: " + userId);
        return activityLogRepository.findByUserId(userId)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ActivityLog convertToDto(ActivitylogEntity activitylogEntity) {
        ActivityLog dto = new ActivityLog();


        dto.setId(activitylogEntity.getId());
        dto.setCreatedAt(activitylogEntity.getCreatedAt());
        dto.setExerciseType(activitylogEntity.getExerciseType());
        dto.setDuration(activitylogEntity.getDuration());
        dto.setDifficultyLevel(activitylogEntity.getDifficultyLevel().name());
        dto.setResult(activitylogEntity.getResult());
        dto.setUser(convertToUserDto(activitylogEntity.getUser()));

        return dto;
    }


    private User convertToUserDto(UserEntity userEntity) {
        User userDto = new User();
        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());


        return userDto;
    }

    public void deleteActivityLog(Integer id) {
        activityLogRepository.deleteById(id);
        logService.log("WARN", "ActivityLog deleted: " + id);
    }

    public ActivityLog updateActivityLog(Integer id, ActivityLog activityLog) {
        ActivitylogEntity existingActivity = activityLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        existingActivity.setExerciseType(activityLog.getExerciseType());
        existingActivity.setDuration(activityLog.getDuration());
        existingActivity.setDifficultyLevel(DifficultyLevelEnum.valueOf(activityLog.getDifficultyLevel()));
        existingActivity.setResult(activityLog.getResult());
        existingActivity.setCreatedAt(activityLog.getCreatedAt());

         activityLogRepository.save(existingActivity);

        logService.log("INFO", "ActivityLog updated: " + id);
        return findById(existingActivity.getId());
    }

    ActivityLog findById(Integer id){
        logService.log("INFO", "Fetched activity with id: " + id);
        ActivitylogEntity activitylogEntity = activityLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));
         return convertToDto(activitylogEntity);
    }

}
