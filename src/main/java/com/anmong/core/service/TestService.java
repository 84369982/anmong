package com.anmong.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anmong.common.message.CommonException;
import com.anmong.core.dao.UserDAO;
import com.anmong.core.entity.User;


@Service
public class TestService {
	
	@Autowired
	private UserDAO userDAO;
	
	public void add(User user) {
		userDAO.insert(user);
		throw CommonException.businessException("回滚");
	}

}
