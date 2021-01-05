package dts.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import boundaries.UserBoundary;
import dts.converter.UserConverter;
import dts.data.UserEntity;
import models.users.UserId;

//@Service
public class UsersServiceImplementation implements UsersService {

	private Map<String, UserEntity> userStorage;
	private UserConverter userConverter;

	@Autowired
	public void setMessageConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}

	@PostConstruct
	public void init() {
		this.userStorage = Collections.synchronizedMap(new HashMap<>()); // thread safe map
	}

	@Override
	public UserBoundary createUser(UserBoundary user) {
		UserEntity newUser = this.userConverter.toEntity(user);
		userStorage.put(newUser.getUserId().toString(), newUser);
		return this.userConverter.toBoundary(newUser);
	}

	@Override
	public UserBoundary login(String userSpace, String userEmail) {
		UserEntity existingUser = userStorage.get(new UserId(userSpace, userEmail).toString());
		if (existingUser == null) {
			throw new RuntimeException("user with email: " + userEmail + "does not exist");
		}
		return userConverter.toBoundary(existingUser);
	}

	@Override
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) {
		UserEntity existingUser = userStorage.get(new UserId(userSpace, userEmail).toString());
		if (existingUser == null) {
			throw new RuntimeException("user with email: " + userEmail + "does not exist");
		}
		UserEntity updatedUser = this.userConverter.toEntity(update);
		if (update.getUsername() != null)
			existingUser.setUsername(updatedUser.getUsername());
		if (update.getAvatar() != null)
			existingUser.setAvatar(updatedUser.getAvatar());
		if (update.getRole() != null)
			existingUser.setRole(updatedUser.getRole());
		this.userStorage.put(existingUser.getUserId(), existingUser);
		return this.userConverter.toBoundary(existingUser);
	}

	@Override
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail, int size, int page) {
		return this.userStorage.values() // Collection<UserEntity>
				.stream() // Stream<UserEntity>
				.map(entity -> userConverter.toBoundary(entity))// Stream<UserBoundary>
				.collect(Collectors.toList()); // List<UserBoundary>
	}

	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		this.userStorage.clear();
	}

}
