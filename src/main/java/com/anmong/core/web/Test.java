package com.anmong.core.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.core.dao.UserDAO;
import com.anmong.core.entity.User;
import com.anmong.core.service.TestService;

@RestController
@RequestMapping("test")
public class Test {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	private TestService testService;
	
	@GetMapping("test")
	public List<User> test() {
		return userDAO.selectWhere("1 = ?", 1);
	}
	
	@GetMapping("page-list")
	public ResultPage<User> pageList(int page,int perPage){
		return userDAO.getSQL()
				.SELECT_all_FROM("users")
				.queryPage(page, perPage, User.class);
				
	}
	
	@GetMapping("add")
	public void add() {
		User user = new User();
		user.setId("sdf123");
		user.setNickname("zasdfsdf");
		testService.add(user);
	}
	
	

}
