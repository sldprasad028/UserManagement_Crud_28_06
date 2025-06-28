package com.techpixe.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techpixe.dto.UpdateUserRequestDTO;
import com.techpixe.dto.UserResponseDTO_Record;
import com.techpixe.entity.User;
import com.techpixe.exception.EmailAlreadyExistsException;
import com.techpixe.exception.UserNotFoundException;
import com.techpixe.repository.UserRepository;
import com.techpixe.service.UserService;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public void saveUser(String userName, String email, String mobileNumber, String city, String password)
	{
		 boolean isTrue= userRepository.existsByEmail(email);
		 if (isTrue)
		 {
			 throw new EmailAlreadyExistsException(email);
		 }
		
		User user = new User();
		user.setUserName(userName);
		user.setEmail(email);
		user.setMobileNumber(mobileNumber);
		user.setCity(city);
		user.setPassword(password);
		
		userRepository.save(user);
	}

	//With Record
	@Override
	public UserResponseDTO_Record getUserById(Long userId)
	{
	    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	    return UserResponseDTO_Record.fromEntity(user);
	}

	@Override
	public List<User> getAll()
	{
		return userRepository.findAll();
	}

	@Override
	public Page<User> getAllUsersWithPagination(int offSet, int pageSize)
	{
		return userRepository.findAll(PageRequest.of(offSet, pageSize));
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
	public void updateUser4(Long userId, UpdateUserRequestDTO updateRequest) {
	    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

	    boolean isUpdated = false;

	    if (updateRequest.getUserName() != null && !updateRequest.getUserName().equals(user.getUserName())) {
	        user.setUserName(updateRequest.getUserName());
	        isUpdated = true;
	    }

	    if (updateRequest.getMobileNumber() != null && !updateRequest.getMobileNumber().equals(user.getMobileNumber())) {
	        user.setMobileNumber(updateRequest.getMobileNumber());
	        isUpdated = true;
	    }

	    if (updateRequest.getCity() != null && !updateRequest.getCity().equals(user.getCity())) {
	        user.setCity(updateRequest.getCity());
	        isUpdated = true;
	    }

	    if (isUpdated) {
	        user.setUpdatedAt(LocalDateTime.now());
	        userRepository.save(user);
	    } else {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No changes provided for update");
	    }
	}



}
