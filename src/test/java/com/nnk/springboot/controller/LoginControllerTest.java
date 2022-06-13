package com.nnk.springboot.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {

	UserService userService;

	@Test
	public void loginTest() {
		LoginController c = new LoginController();
		ModelAndView mav = c.login();
		Assert.assertEquals("login", mav.getViewName());
	}

	@Test
	public void logoutTest() {
		LoginController c = new LoginController();
		ModelAndView mav = c.logout();
		Assert.assertEquals("home", mav.getViewName());
	}
}
