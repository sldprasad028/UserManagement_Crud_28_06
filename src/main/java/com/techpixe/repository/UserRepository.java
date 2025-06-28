package com.techpixe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.entity.User;

public interface UserRepository extends JpaRepository<User, Long>
{
	public boolean existsByEmail(String email);
}
