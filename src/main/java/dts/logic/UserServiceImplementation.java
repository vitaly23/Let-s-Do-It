package dts.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import boundaries.UserBoundary;
import logic.UserConverter;
import dts.data.UserEntity;
import models.users.User;

@Service
public class UserServiceImplementation implements UsersService {

	private String spaceName;
	private Map<User, UserEntity> userStorage;
	private UserConverter userConverter;
	
	@Autowired
	public void setMessageConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}
	
	@Value("${spring.application.name:deafult}")
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	
	@PostConstruct
	public void init() {
		this.userStorage = Collections.synchronizedMap(new HashMap<>()); // thread safe map
	}
	
	@Override
	public UserBoundary createUser(UserBoundary user) {
		UserBoundary newUser = new UserBoundary();
		newUser.setAvatar(user.getAvatar());
		newUser.setRole(user.getRole());
		newUser.setUserId(user.getUserId());
		newUser.setUsername(user.getRole().toString());
		userStorage.put(newUser.getUserId(), userConverter.toEntity(newUser));
		return newUser; 
	}

	@Override
	public UserBoundary login(String userSpace, String userEmail) {
		User uid = new User();
		if(userSpace != null && userEmail != null) {
			uid.setEmail(userEmail);
			uid.setSpace(userSpace);
		}
		
		if(uid != null && userStorage.containsKey(uid)){
			return userConverter.toBoundary(userStorage.get(uid));
		}
		else 
			return null;
	}

	@Override
	public UserBoundary updateUser(UserBoundary update, String userSpace, String userEmail) {
		User uid = new User();
		if(userSpace != null && userEmail != null) {
			uid.setEmail(userEmail);
			uid.setSpace(userSpace);
		}
		
		if (uid != null && userStorage.containsKey(uid)) {
			UserBoundary current = userConverter.toBoundary(userStorage.get(uid)); 
			current.setAvatar(update.getAvatar());
			current.setRole(update.getRole());
			current.setUserId(update.getUserId());
			current.setUsername(update.getRole().toString());
			userStorage.put(uid, userConverter.toEntity(current));
		}
		return null;
	}

	@Override
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		if(adminSpace != null && adminEmail != null) {
			return this.userStorage.values() // Collection<UserEntity>
					.stream() // Stream<UserEntity>
					.map(entity -> 
					userConverter.toBoundary(entity))// Stream<MessageBoundary>
					.collect(Collectors.toList()); // List<UserBoundary> 
		}
		return new ArrayList<UserBoundary>();
	}

	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		this.userStorage.clear();
	}

	public String getSpaceName() {
		return spaceName;
	}
	
}
