package com.techpixe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.techpixe.dto.UpdateUserRequestDTO;
import com.techpixe.dto.UserRequestDTO;
import com.techpixe.dto.UserResponseDTO_Record;
import com.techpixe.entity.User;
import com.techpixe.exception.ApiResponse;
import com.techpixe.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController 
{
	@Autowired
	private UserService userService;

	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse<Void>> addUser2(@Valid @RequestBody UserRequestDTO userRequestDTO) 
	{
	    userService.saveUser(userRequestDTO);
	    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED.value(), "User saved successfully", null));
	}

	
	@GetMapping("/get-by/{userId}")
	public ResponseEntity<ApiResponse<UserResponseDTO_Record>> getUserDetails(@PathVariable Long userId)
	{
	    UserResponseDTO_Record userResponseDTO_Record = userService.getUserById(userId);
	    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "User fetched successfully", userResponseDTO_Record));
	}

	
	@GetMapping("/get-all")
	public ResponseEntity<ApiResponse<List<UserResponseDTO_Record>>> fetchAll() 
	{
	    List<UserResponseDTO_Record> fetchedData = userService.getAll();
	    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "All users fetched successfully", fetchedData));
	}

	
	@GetMapping("/get-all2")
	public ResponseEntity<ApiResponse<List<UserResponseDTO_Record>>> fetchAll2()
	{
	    List<UserResponseDTO_Record> fetchedData = userService.getAll();
	    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "All users (DTO) fetched successfully", fetchedData));
	}

	
	@DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId)
	{
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(),"User deleted successfully", null ));
    }
	
	//-----------UPDATE--------------------------------UPDATE-------------------------------UPDATE-----------------------------------------UPDATE------------------------------------------------------
	//Model-4 **** Chat gpt give (industry standard)
	@PatchMapping("/update/{userId}")
	public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable Long userId,@Validated @RequestBody UpdateUserRequestDTO updateRequest) 
	{
	    userService.updateUser4(userId, updateRequest); // no return needed
	    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "User updated successfully", null));
	}
	
	//---------------------------
	
	@GetMapping("/sorted-by/{field}")
	public ResponseEntity<ApiResponse<List<UserResponseDTO_Record>>> fetchAllSortingOnCity(@PathVariable String field)
	{
		List<UserResponseDTO_Record> fetchedAllSortingData = userService.fetchAllUserswithSorting(field);
		return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value()," fetched Successfully",fetchedAllSortingData));
	}

	@GetMapping("/paginated/{offSet}/{pageSize}")
	public ResponseEntity<ApiResponse<Page<UserResponseDTO_Record>>> fetchAllUsersWithPagination(@PathVariable int offSet, @PathVariable int pageSize)
	{ 
	    Page<UserResponseDTO_Record> fetchAllPaginatedUsersData = userService.fetchAllUsersWithPagination(offSet, pageSize);
	    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Paginated users fetched successfully", fetchAllPaginatedUsersData));
	}
	
	@GetMapping("/paginated-sorted/{offSet}/{pageSize}/{field}")
	public ResponseEntity<ApiResponse<Page<UserResponseDTO_Record>>> fetchAllUsersWithPaginationAndSorting(@PathVariable int offSet, @PathVariable int pageSize,@PathVariable String field)
	{
		Page<UserResponseDTO_Record> paginationWithSorting = userService.fetchAllUsersWithPaginationAndSorting(offSet, pageSize, field);
		return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Paginated users fetched successfully with Sorting" , paginationWithSorting));
	}
	
}
