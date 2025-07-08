package com.techpixe.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.techpixe.entity.User;

public record UserResponseDTO_Record(Long userId,String userName,String email) 
{
	
	public static UserResponseDTO_Record fromEntity(User user)
	{
		return new UserResponseDTO_Record(user.getUserId(), user.getUserName(), user.getEmail());
	}
	
	public static List<UserResponseDTO_Record> fromEntityList(List<User> users)
	{
        return users.stream()
                .map(UserResponseDTO_Record::fromEntity)
                .collect(Collectors.toList()); // Use .toList() if you're using Java 16+
    }
	
	
	public static Page<UserResponseDTO_Record> fromEntityPage(Page<User> userPage)
	{
	    List<UserResponseDTO_Record> content = userPage.getContent()
	            .stream()
	            .map(UserResponseDTO_Record::fromEntity)
	            .collect(Collectors.toList());

	    return new PageImpl<>(content, userPage.getPageable(), userPage.getTotalElements());
	}

	
	
}
