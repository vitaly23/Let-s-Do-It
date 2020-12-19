package dts.utils;

import java.util.Optional;

import dts.dao.UserDao;
import dts.data.UserEntity;
import dts.data.UserRole;
import exceptions.AdminNotFoundException;
import exceptions.InvalidUserException;
import exceptions.UserNotFoundException;
import models.users.UserId;

public class ValidationService {
	private UserDao userDao;

	public ValidationService(UserDao userDao)
	{
		this.userDao = userDao;

	}
	
	public Exception ValidateUserExists(UserEntity userEntity) {
		Exception ex = new Exception();
		Optional<UserEntity> existingUser = this.userDao.findById(new UserId(userEntity.getUsername(), userEntity.getUserId()).toString());
		if (!existingUser.isPresent())
		{			
			ex = new UserNotFoundException("user with email: " + userEntity.getUserId() + "does not exist");
		}
		return ex;
	}
	
	public Exception ValidateUserData(UserEntity userEntity)
	{
		Exception ex = new Exception();
		
		if(userEntity.getUserId().isEmpty() || 
				userEntity.getUsername().isEmpty() ||
				userEntity.getAvatar().isEmpty() ||
				userEntity.getAvatar() == null ||
				userEntity.getUserId() == null ||
				userEntity.getUsername() == null ||
				userEntity.getAvatar() == null ||
				!userEntity.getUserId().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$") ||
				!(userEntity.getRole().equals(UserRole.ADMIN) ||
				  userEntity.getRole().equals(UserRole.MANAGER) ||
				  userEntity.getRole().equals(UserRole.PLAYER)))
				{
					ex = new InvalidUserException("Invalid email: " + userEntity.getUserId()
							+ " or user name: " + userEntity.getUsername());
				}
		return ex;
	}
	
	public Exception ValidateAdmin(String adminSpace, String adminEmail) {
		Exception ex = new Exception();
		Optional<UserEntity> existingAdmin = this.userDao.findById(new UserId(adminSpace, adminEmail).toString());
		if(!existingAdmin.isPresent())
		{
			ex = new AdminNotFoundException("Admin with email: " + existingAdmin + "does not exist");
		}
		UserEntity existingAdminEntity = existingAdmin.get();
		if(!existingAdminEntity.getRole().equals(UserRole.ADMIN))
		{
			ex = new InvalidUserException("Invalid role: " + existingAdminEntity.getRole()
			+ " for user: " + existingAdminEntity.getUsername());
		}
		return ex;
	}
}
