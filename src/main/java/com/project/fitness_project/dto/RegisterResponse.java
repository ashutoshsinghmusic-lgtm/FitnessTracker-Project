package com.project.fitness_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
