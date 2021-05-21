package com.ocr.demo.repository;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ocr.demo.dao.UserDao;
import com.ocr.demo.domain.UserInfo;
import com.ocr.demo.entity.UserEntity;

@Service
public class UserRepositoryImpl {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;
    
    @Transactional
    public UserDetails loadUserByUsername(String username) {
		UserEntity entity = userDao.getUserByUsername(username);
		if (entity != null) {
			return new User(entity.getUsername(), entity.getPassword(),	new ArrayList<>());
		}
		return null;
    }

	@Transactional
	public UserInfo getUserInfoByUsername(String username) {
		UserEntity entity = userDao.getUserByUsername(username);
		if (entity != null) {
			return modelMapper.map(entity, UserInfo.class);
		}
		return null;
	}
}
