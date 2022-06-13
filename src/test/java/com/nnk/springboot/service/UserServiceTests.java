package com.nnk.springboot.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	User user = new User("Lara", "Laracer=1", "Lara", "admin");

	@Test
	public void getAllUsersTest() {
		List<User> users = new ArrayList<>();
		users.add(user);
		user = userService.addUser(user);
		Mockito.when(userRepository.findAll()).thenReturn(users);
		List<User> usersresult = userService.getAllUsers();
		assertEquals(users, usersresult);
	}

	@Test
	public void updateUserTest() {
		user.setFullname("Update");
		Mockito.when(userRepository.save(user)).thenReturn(user);
		User userResult = userService.addUser(user);
		assertEquals(user, userResult);
	}

	@Test
	public void findUserByIdTest() {
		Integer id = user.getId();
		Optional<User> userOptional = userService.findUserById(id);
		Optional<User> userResult = userRepository.findById(id);
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		assertEquals(userOptional, userResult);
	}

	@Test
	public void deleteUserTest() {
		boolean userResultat = userService.deleteUser(user);
		assertTrue(userResultat);
	}
}
