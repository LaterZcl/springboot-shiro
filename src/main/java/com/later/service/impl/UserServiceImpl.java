package com.later.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.later.domain.User;
import com.later.mapper.UserMapper;
import com.later.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired UserMapper userMapper;

	@Override
	public User findByName(String name) {
		return userMapper.findByName(name);
	}

	@Override
	public User findById(Long id) {
		return userMapper.findById(id);
	}

}
