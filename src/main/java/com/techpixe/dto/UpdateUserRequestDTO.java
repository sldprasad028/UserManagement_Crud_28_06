package com.techpixe.dto;

import com.techpixe.entity.User;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDTO 
{
	@Size(min = 2, max = 8, message = "Username must be between 2 and 8 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Username must contain only letters and spaces")
    private String userName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits")
    private String mobileNumber;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "City must contain only letters and spaces")
    private String city;
    
 // ✅ Static mapper method to convert User -> UpdateUserRequestDTO
    public static UpdateUserRequestDTO fromEntity(User user) 
    {
        return new UpdateUserRequestDTO(user.getUserName(),user.getMobileNumber(),user.getCity());
    }
}

