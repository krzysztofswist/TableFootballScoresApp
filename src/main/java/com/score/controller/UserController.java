package com.score.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.score.message.Response;
import com.score.model.User;
import com.score.repo.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/find/all/users")
	public Response findAll() {

		Iterable<User> users = userRepository.findAll();
		List<String> userList=new ArrayList<>();
		for(User user:users) {
			userList.add(user.getUserId());
		}
		Collections.sort(userList);

		return new Response("Done", userList);
	}

}
