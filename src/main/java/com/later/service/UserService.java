package com.later.service;

import com.later.domain.User;

public interface UserService {

	public User findByName(String name);
	
	public User findById(Long id);
}
