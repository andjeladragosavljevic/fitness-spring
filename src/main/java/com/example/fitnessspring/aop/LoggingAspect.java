package com.example.fitnessspring.aop;

import com.example.fitnessspring.services.LogService;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAspect {
    private final LogService logService;
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    public LoggingAspect(LogService logService) {
        this.logService = logService;
    }

    @Before("execution(* com.example.fitnessspring.services.*.*(..))")
    public void logBeforeServiceMethods() {
        logService.log("INFO", "Service method called");
        logger.info("Service method called");
    }

    @AfterThrowing(pointcut = "execution(* com.example.fitnessspring.services.*.*(..))", throwing = "exception")
    public void logAfterThrowing(Exception exception) {
        logService.log("ERROR", exception.getMessage());
        logger.error("Exception occurred: " + exception.getMessage());
    }
}
