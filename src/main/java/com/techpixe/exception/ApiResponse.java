package com.techpixe.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in JSON
public class ApiResponse<T>
{

    private boolean success;
    private int statusCode;
    private String message;

    private T data;     // Used for success responses
    private T errors;   // Used for failure responses

    // ✅ Success response factory method
    public static <T> ApiResponse<T> success(int statusCode, String message, T data) 
    {
        return new ApiResponse<>(true, statusCode, message, data, null);
    }

    // ✅ Failure without specific error payload
    public static <T> ApiResponse<T> failure(int statusCode, String message)
    {
        return new ApiResponse<>(false, statusCode, message, null, null);
    }

    // ✅ Failure with specific error payload (e.g. validation errors)
    public static <T> ApiResponse<T> failure(int statusCode, String message, T errors) 
    {
        return new ApiResponse<>(false, statusCode, message, null, errors);
    }
}
