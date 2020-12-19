package dts.utils;

import java.util.Optional;

import org.springframework.stereotype.Component;

import constants.Constants;
import dts.dao.UserDao;
import dts.data.UserEntity;
import dts.data.UserRole;
import exceptions.InvalidUserException;
import exceptions.RoleViolationException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;
import models.users.UserId;

@Component
public class ValidationService {
	private UserDao userDao;

	public ValidationService()
	{
		
	}
	
	public ValidationService(UserDao userDao)
	{
		this.userDao = userDao;

	}
	
	public void ValidateNotSuchUser(UserEntity userEntity, Optional<UserEntity> existingUser) {
		if (existingUser.isPresent())
		{			
			throw new UserAlreadyExistsException("User with email: " +
					userEntity.getUserId() +	" and name " +
					userEntity.getUsername() + " already exists");
		}
	}
	
	public void ValidateUserFound(Optional<UserEntity> userEntity, String userId) {
		if (!userEntity.isPresent())
		{			
			throw new UserNotFoundException("user with email: " + userId + "does not exist");
		}
	}
	
	public void ValidateUserData(UserEntity userEntity)
	{
		
		if(userEntity.getUserId().isEmpty() || 
				userEntity.getUsername().isEmpty() ||
				userEntity.getAvatar().isEmpty() ||
				userEntity.getAvatar() == null ||
				userEntity.getUserId() == null ||
				userEntity.getUsername() == null ||
				userEntity.getAvatar() == null ||
				!userEntity.getUserId().split(Constants.DELIMITER)[1].matches("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$") ||
				!(userEntity.getRole().equals(UserRole.ADMIN) ||
				  userEntity.getRole().equals(UserRole.MANAGER) ||
				  userEntity.getRole().equals(UserRole.PLAYER)))
				{
					throw new InvalidUserException("Invalid email: " + userEntity.getUserId()
							+ " or user name: " + userEntity.getUsername());
				}
	}
	
	public void ValidateRole(Optional<UserEntity> user, UserRole role) {
		UserEntity entity = user.get();
		if(!entity.getRole().equals(role))
		{
			throw new RoleViolationException("Invalid role: " + entity.getRole()
			+ " for user: " + entity.getUsername());
		}
	}
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
