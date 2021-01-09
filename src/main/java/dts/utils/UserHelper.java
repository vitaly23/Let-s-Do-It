package dts.utils;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import constants.Constants;
import dts.Application;
import dts.dao.UserDao;
import dts.data.UserEntity;
import dts.data.UserRole;

import exceptions.InvalidUserException;
import exceptions.RoleViolationException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;

import models.users.UserId;

@Component
public class UserHelper {

	private Log log = LogFactory.getLog(Application.class);
	private UserDao userDao;
	
	@Autowired
	public UserHelper(UserDao userDao) {
		this.userDao = userDao;
	}

	public void validateUserFound(Optional<UserEntity> optionalUser, String userEmail) {
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User with email: " + userEmail + " does not exist");
		}
	}

	public void validateRole(UserEntity userEntity, UserRole role) {
		if (!userEntity.getRole().equals(role)) {
			throw new RoleViolationException(
					"User: " + userEntity.getUserId() + " has insufficient privileges to complete the operation");
		}
	}

	public void validateUserData(UserEntity userEntity) {
		if (userEntity.getUserId() == null || userEntity.getUserId().isEmpty() || userEntity.getUsername() == null
				|| userEntity.getUsername().isEmpty() || userEntity.getAvatar() == null
				|| userEntity.getAvatar().isEmpty()
				|| !(userEntity.getRole().equals(UserRole.ADMIN) || userEntity.getRole().equals(UserRole.MANAGER)
						|| userEntity.getRole().equals(UserRole.PLAYER))) {
			throw new InvalidUserException("User email, username, avatar, role can not be null or empty");
		}
	}

	public void validateNewUserData(UserEntity userEntity) {
		this.validateUserData(userEntity);
		String userEmail = userEntity.getUserId().split(Constants.DELIMITER)[1];
		if (!userEmail.matches(
				"^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$")) {
			throw new InvalidUserException("User email address is invalid");
		}
	}

	@Transactional(readOnly = true)
	public void validateNoSuchUser(String userId) {
		Optional<UserEntity> optionalUser = this.userDao.findById(userId);
		if (optionalUser.isPresent()) {
			throw new UserAlreadyExistsException("User with userId: " + userId + " already exists");
		}
	}

	@Transactional(readOnly = true)
	public UserEntity getSpecificUser(String userSpace, String userEmail) {
		Optional<UserEntity> optionalUser = this.userDao.findById(new UserId(userSpace, userEmail).toString());
		this.validateUserFound(optionalUser, userEmail);
		return optionalUser.get();
	}

	@Transactional(readOnly = true)
	public UserEntity getSpecificUserWithRole(String userSpace, String userEmail, UserRole role) {
		UserEntity existingUser = this.getSpecificUser(userSpace, userEmail);
		this.validateRole(existingUser, role);
		return existingUser;
	}

	@Transactional
	public void changeUserRole(UserEntity userEntity, UserRole role) {
		this.log.debug("Setting user with userId '" + userEntity.getUserId() + "' to " + role);
		userEntity.setRole(role);
		this.userDao.save(userEntity);
	}

}
