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



// @JsonInclude(JsonInclude.Include.NON_NULL) ::  Only include non-null fields in JSON
											//This tells Jackson (the JSON library) to exclude null fields from the JSON response.

// ApiResponse<T>  :: <T> makes the class generic, meaning it can handle any type of data (String, Integer, custom objects, lists, etc.).


//private T data;
// 1) This holds the response body in case of a success.
// 2) T allows flexibility — this can be any object type depending on what you're sending back.

// private T errors;
// 1) This holds error details (like validation messages) in case of a failure.
// 2) Again, T gives flexibility — the errors could be a string, a list of messages, a map, etc.

//Note :  T is a placeholder that gets replaced with a real class when you use the ApiResponse.

