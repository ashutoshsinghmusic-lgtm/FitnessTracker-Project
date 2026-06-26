package com.project.fitness_project.dto;

import com.project.fitness_project.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&^#()_+\\-={}\\[\\]:;\"'<>,./]).{8,32}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;

    @NotBlank(message = "First Name is required")
    @Pattern(
            regexp = "^[A-Za-z]+$",
            message = "Name must contain only letters"
    )
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Pattern(
            regexp = "^[A-Za-z]+$",
            message = "Name must contain only letters"
    )
    private String lastName;
    private UserRole role;
}
