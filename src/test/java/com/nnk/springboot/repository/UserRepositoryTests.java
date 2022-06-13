package com.nnk.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	UserRepository userRepository;

	@Test
	public void userTests() {
		User user = new User("Lara", "Laracer=1", "Lara", "admin");

		user = userRepository.save(user);
		Assert.assertNotNull(user.getId());
		Assert.assertTrue(user.getFullname().equals("Lara"));

		user.setFullname("Lara test");
		user = userRepository.save(user);
		Assert.assertTrue(user.getFullname().equals("Lara test"));

		List<User> userList = userRepository.findAll();
		Assert.assertTrue(userList.size() > 0);

		Integer id = user.getId();
		userRepository.delete(user);
		Optional<User> userOpt = userRepository.findById(id);
		Assert.assertFalse(userOpt.isPresent());
	}

}
