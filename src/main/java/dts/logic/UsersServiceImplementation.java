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
import constants.Constants;
import dts.converter.UserConverter;
import dts.data.UserEntity;

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
		UserEntity existingUser = userStorage.get(userSpace + Constants.DELIMITER + userEmail);
		if (existingUser == null) {
			throw new RuntimeException("user with email: " + userEmail + "does not exist");
		}
		return userConverter.toBoundary(existingUser);
	}

	@Override
	public UserBoundary updateUser(UserBoundary update, String userSpace, String userEmail) {
		UserEntity existingUser = userStorage.get(userSpace + Constants.DELIMITER + userEmail);
		if (existingUser == null) {
			throw new RuntimeException("user with email: " + userEmail + "does not exist");
		}
		UserEntity userEntity = this.userConverter.toEntity(update);
		userEntity.setUserId(existingUser.getUserId().toString());
		this.userStorage.put(userEntity.getUserId().toString(), userEntity);
		return this.userConverter.toBoundary(userEntity);
	}

	@Override
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		return this.userStorage.values() // Collection<UserEntity>
				.stream() // Stream<UserEntity>
				.map(entity -> userConverter.toBoundary(entity))// Stream<MessageBoundary>
				.collect(Collectors.toList()); // List<UserBoundary>
	}

	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		this.userStorage.clear();
	}

}
