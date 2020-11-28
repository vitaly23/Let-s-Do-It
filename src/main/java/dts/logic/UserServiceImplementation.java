package dts.logic;

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
	public UserBoundary createUser(UserBoundary UserBoundary) {
		UserBoundary newUser = new UserBoundary();
		newUser.setAvatar(UserBoundary.getAvatar());
		newUser.setRole(UserBoundary.getRole());
		newUser.setUserId(UserBoundary.getUserId());
		newUser.setUsername(UserBoundary.getRole().toString());
		userStorage.put(newUser.getUserId(), userConverter.toEntity(newUser));
		return newUser; 
	}

	@Override
	public UserBoundary login(String UserSpace, String userEmail) {
		User uid = new User();
		if(UserSpace != null && userEmail != null) {
			uid.setEmail(userEmail);
			uid.setSpace(UserSpace);
		}
		
		if(uid != null && userStorage.containsKey(uid)){
			return userConverter.toBoundary(userStorage.get(uid));
		}
		else 
			return null;
	}

	@Override
	public UserBoundary updateUser(UserBoundary UserBoundary, String UserSpace, String userEmail) {
		User uid = new User();
		if(UserSpace != null && userEmail != null) {
			uid.setEmail(userEmail);
			uid.setSpace(UserSpace);
		}
		
		if (uid != null && userStorage.containsKey(uid)) {
			UserBoundary current = userConverter.toBoundary(userStorage.get(uid)); 
			current.setAvatar(UserBoundary.getAvatar());
			current.setRole(UserBoundary.getRole());
			current.setUserId(UserBoundary.getUserId());
			current.setUsername(UserBoundary.getRole().toString());
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
		return null;
	}

	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		this.userStorage.clear();
	}

	public String getSpaceName() {
		return spaceName;
	}
	
}
