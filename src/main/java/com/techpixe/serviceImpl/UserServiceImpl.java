package com.techpixe.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techpixe.dto.UpdateUserRequestDTO;
import com.techpixe.dto.UserRequestDTO;
import com.techpixe.dto.UserResponseDTO_Record;
import com.techpixe.entity.User;
import com.techpixe.enums.Role;
import com.techpixe.exception.EmailAlreadyExistsException;
import com.techpixe.exception.UserNotFoundException;
import com.techpixe.repository.UserRepository;
import com.techpixe.service.UserService;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public void saveUser(UserRequestDTO userRequestDTO)
	{
		 boolean isTrue= userRepository.existsByEmail(userRequestDTO.getEmail());
		 if (isTrue)
		 {
			 throw new EmailAlreadyExistsException(userRequestDTO.getEmail());
		 }
		
		User user = new User();
		user.setUserName(userRequestDTO.getUserName());
		user.setEmail(userRequestDTO.getEmail());
		user.setMobileNumber(userRequestDTO.getMobileNumber());
		user.setCity(userRequestDTO.getCity());
		user.setCreatedAt(LocalDateTime.now());
		user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
		user.setRole(Role.USER);
		
		userRepository.save(user);
	}

	//With Record
	@Override
	public UserResponseDTO_Record getUserById(Long userId)
	{
	    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	    return UserResponseDTO_Record.fromEntity(user);
	}
	
	//With DTO Class.
	@Override
	public UpdateUserRequestDTO getUserById1(Long userId) 
	{
	    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	    return UpdateUserRequestDTO.fromEntity(user);
	}


	@Override
	public List<UserResponseDTO_Record> getAll()
	{
		List<User> fetchedAllUsers = userRepository.findAll();
		return UserResponseDTO_Record.fromEntityList(fetchedAllUsers);
	}

	
	
	
	// Internal use: returns the entity
	private User getUserEntityById(Long userId)
	{
	    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}



	@Override
	public void deleteUser(Long userId) 
	{
		User user = getUserEntityById(userId);
		userRepository.delete(user);
	}

	
	
	//Model-4 (Correct industry Standard)
//	@Override
//	public void updateUser4(Long userId, UpdateUserRequestDTO updateRequest)
//	{
//	    User user = userRepository.findById(userId)
//	            .orElseThrow(() -> new UserNotFoundException(userId));
//
//	    if (updateRequest.getUserName() != null)
//	    {
//	        user.setUserName(updateRequest.getUserName());
//	    }
//	    if (updateRequest.getMobileNumber() != null)
//	    {
//	        user.setMobileNumber(updateRequest.getMobileNumber());
//	    }
//	    if (updateRequest.getCity() != null)
//	    {
//	        user.setCity(updateRequest.getCity());
//	    }
//
//	    user.setUpdatedAt(LocalDateTime.now());
//	    userRepository.save(user); // Save and return nothing
//	}
	
	@Override
	public void updateUser4(Long userId, UpdateUserRequestDTO updateRequest)
	{
	    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	    boolean isUpdated = false;

	    if (updateRequest.getUserName() != null && !updateRequest.getUserName().equals(user.getUserName())) 
	    {
	        user.setUserName(updateRequest.getUserName());
	        isUpdated = true;
	    }
	    if (updateRequest.getMobileNumber() != null && !updateRequest.getMobileNumber().equals(user.getMobileNumber()))
	    {
	        user.setMobileNumber(updateRequest.getMobileNumber());
	        isUpdated = true;
	    }
	    if (updateRequest.getCity() != null && !updateRequest.getCity().equals(user.getCity())) 
	    {
	        user.setCity(updateRequest.getCity());
	        isUpdated = true;
	    }
	    if (isUpdated) 
	    {
	        user.setUpdatedAt(LocalDateTime.now().withSecond(0).withNano(0));
	        userRepository.save(user);
	    }
	    else
	    {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No changes provided for update");
	    }
	}
	
	
	//-------------------------------------

	@Override
	public List<UserResponseDTO_Record> fetchAllUserswithSorting(String field) 
	{
		List<User> all = userRepository.findAll(Sort.by(Sort.Direction.ASC,field));
		return UserResponseDTO_Record.fromEntityList(all);
	}
	
	@Override
	public Page<UserResponseDTO_Record> fetchAllUsersWithPagination(int offSet, int pageSize)
	{
		Page<User> allUsersData = userRepository.findAll(PageRequest.of(offSet, pageSize));
		return UserResponseDTO_Record.fromEntityPage(allUsersData);
	}

	@Override
	public Page<UserResponseDTO_Record> fetchAllUsersWithPaginationAndSorting(int offSet, int pageSize, String field)
	{
		Page<User> allUsers = userRepository.findAll(PageRequest.of(offSet, pageSize).withSort(Sort.by(field)));
		return UserResponseDTO_Record.fromEntityPage(allUsers);
	}

}
