package com.techpixe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.techpixe.dto.UpdateUserRequestDTO;
import com.techpixe.dto.UserRequestDTO;
import com.techpixe.dto.UserResponseDTO_Record;
import com.techpixe.entity.User;

public interface UserService 
{
	public void saveUser(UserRequestDTO userRequestDTO);
	
	//With Record
	public UserResponseDTO_Record getUserById(Long userId);
	
	//With DTO Class.
	public UpdateUserRequestDTO getUserById1(Long userId);
	
	public List<UserResponseDTO_Record> getAll();
	
	public Page<UserResponseDTO_Record> getAllUsersWithPagination(int offSet,int pageSize);
	
	public void deleteUser(Long userId);
	
	public void updateUser4(Long userId, UpdateUserRequestDTO updateRequest);
	
	
}
