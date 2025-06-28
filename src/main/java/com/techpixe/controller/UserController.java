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
import com.techpixe.dto.UserResponseDTO_Record;
import com.techpixe.entity.User;
import com.techpixe.exception.ApiResponse;
import com.techpixe.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController 
{
	@Autowired
	private UserService userService;
	
//	@PostMapping("/add")
//    public ResponseEntity<ApiResponse<Object>> addUser2(@RequestBody User user) 
//	{
//        userService.saveUser(user.getUserName(), user.getEmail(), user.getMobileNumber(), user.getCity(), user.getPassword());
//        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED.value(),"User saved successfully", null));
//    }
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse<Object>> addUser2(@Valid @RequestBody User user) 
	{
	    userService.saveUser(user.getUserName(), user.getEmail(), user.getMobileNumber(), user.getCity(), user.getPassword());
	    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED.value(), "User saved successfully", null));
	}

	
	
	@GetMapping("/fetchById/{userId}")
	public ResponseEntity<ApiResponse<UserResponseDTO_Record>> getUserDetails(@PathVariable Long userId)
	{
	    UserResponseDTO_Record userResponseDTO_Record = userService.getUserById(userId);
	    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "User fetched successfully", userResponseDTO_Record));
	}

	
	
	@GetMapping("/fetchAll")
	public ResponseEntity<ApiResponse<List<User>>> fetchAll() 
	{
	    List<User> fetchedData = userService.getAll();
	    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "All users fetched successfully", fetchedData));
	}

	
	@GetMapping("/fetchAll2")
	public ResponseEntity<ApiResponse<List<UserResponseDTO_Record>>> fetchAll2()
	{
	    List<User> fetchedData = userService.getAll();
	    List<UserResponseDTO_Record> fetchDtoList = UserResponseDTO_Record.fromEntityList(fetchedData);
	    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "All users (DTO) fetched successfully", fetchDtoList));
	}


	@GetMapping("/fetchAllUsersWithPagination/{offSet}/{pageSize}")
	public ResponseEntity<ApiResponse<Page<UserResponseDTO_Record>>> fetchAllUsersWithPagination(@PathVariable int offSet, @PathVariable int pageSize)
	{ 
	    Page<User> fetchAllPaginatedUsersData = userService.getAllUsersWithPagination(offSet, pageSize);
	    Page<UserResponseDTO_Record> dtoPage = UserResponseDTO_Record.fromEntityPage(fetchAllPaginatedUsersData);
	   
	    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Paginated users fetched successfully", dtoPage));
	}

	
	@DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId)
	{
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(),"User deleted successfully", null ));
    }
	
	//-----------UPDATE--------------------------------UPDATE-------------------------------UPDATE-----------------------------------------UPDATE------------------------------------------------------
	//Model-4 **** Chat gpt give (industry standard)
	@PatchMapping("/updateModel4/{userId}")
	public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable Long userId,@Validated @RequestBody UpdateUserRequestDTO updateRequest) 
	{
	    userService.updateUser4(userId, updateRequest); // no return needed
	    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "User updated successfully", null));
	}


	
}
