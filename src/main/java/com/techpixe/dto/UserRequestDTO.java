package com.techpixe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO 
{

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 8, message = "Username must be between 2 and 8 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Username must contain only letters and spaces")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits")
    private String mobileNumber;

    @NotBlank(message = "City is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "City must contain only letters and spaces")
    private String city;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$",
    		 message = "Password must contain at least one uppercase, one lowercase, one digit, and one special character"
    		)
    private String password;
}