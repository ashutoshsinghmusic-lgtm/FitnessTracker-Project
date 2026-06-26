package com.project.fitness_project.dto;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomErrorResponse {
        
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

}
