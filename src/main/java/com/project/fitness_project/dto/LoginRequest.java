package com.project.fitness_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Enter a valid Email")
    private String email;

    @NotBlank(message = "Password can not be blank , enter a password")
    private String password;

}
