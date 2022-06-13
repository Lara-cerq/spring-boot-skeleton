package com.nnk.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

	@MockBean
	private UserService userService;

	@Autowired
	MockMvc mockMvc;

	User user = new User(1, "Lara", "Laracer=1", "Lara", "admin");

	@Test
	public void getAllUsersTest() throws Exception {
		List<User> users = new ArrayList<>();
		users.add(user);
		Mockito.when(userService.getAllUsers()).thenReturn(users);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/user/list")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("users"))
				.andExpect(MockMvcResultMatchers.view().name("user/list")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addUserFormTest() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/user/add")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("user/add")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addUserTest() throws Exception {
		Mockito.when(userService.addUser(user)).thenReturn(user);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user/validate")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("user/add")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void showUpdateFormTest() throws Exception {
		Integer id = user.getId();
		user.setFullname("test");
		Mockito.when(userService.findUserById(id)).thenReturn(Optional.of(user));
		Mockito.when(userService.addUser(user)).thenReturn(user);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/user/update/{id}", id)
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.view().name("user/update"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("user")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void updateBidListTest() throws Exception {
		Integer id = user.getId();
		user.setFullname("test");
		Mockito.when(userService.findUserById(id)).thenReturn(Optional.of(user));
		Mockito.when(userService.addUser(user)).thenReturn(user);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user/update/{id}", id.toString())
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void deleteUserTest() throws Exception {
		Integer id = user.getId();
		Mockito.when(userService.findUserById(id)).thenReturn(Optional.of(user));
		Mockito.when(userService.deleteUser(user)).thenReturn(true);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/user/delete/{id}", id)
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
