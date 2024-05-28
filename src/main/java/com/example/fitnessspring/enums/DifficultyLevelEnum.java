package com.example.fitnessspring.enums;

public enum DifficultyLevelEnum {

        Beginner("Beginner"),
        Intermediate("Intermediate"),
        Advanced("Advanced");

        private final String description;

        private DifficultyLevelEnum(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }


}
