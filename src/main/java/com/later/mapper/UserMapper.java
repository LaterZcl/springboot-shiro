package com.later.mapper;

import com.later.domain.User;

public interface UserMapper {

	public User findByName(String name);
	
	public User findById(Long id);
}
