package dts.logic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boundaries.UserBoundary;
import dts.converter.UserConverter;
import dts.dao.UserDao;
import dts.data.UserEntity;
import dts.data.UserRole;
import exceptions.AdminNotFoundException;
import exceptions.InvalidUserException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;
import models.users.UserId;

@Service
public class EnhancedUsersServiceImplementation implements UsersService {

	private UserDao userDao;
	private UserConverter userConverter;

	@Autowired
	public EnhancedUsersServiceImplementation(UserDao userDao, UserConverter userConverter) {
		super();
		this.userDao = userDao;
		this.userConverter = userConverter;
	}

	@Override
	@Transactional
	public UserBoundary createUser(UserBoundary user) {
		UserEntity newUserEntity = this.userConverter.toEntity(user);
		Optional<UserEntity> existingUser = this.userDao.findById(newUserEntity.getUserId());
		if(existingUser.isPresent())
		{
			throw new UserAlreadyExistsException("User with email: " +
					newUserEntity.getUserId() +	" and name " +
					newUserEntity.getUsername() + " already exists");
		}
		if(newUserEntity.getUserId().isEmpty() || 
		   newUserEntity.getUsername().isEmpty() ||
		   newUserEntity.getAvatar().isEmpty() ||
		   newUserEntity.getUserId() == null ||
		   newUserEntity.getUsername() == null ||
		   newUserEntity.getAvatar() == null ||
		   !newUserEntity.getUserId().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$") ||
		   !(newUserEntity.getRole().equals(UserRole.ADMIN) ||
			 newUserEntity.getRole().equals(UserRole.MANAGER) ||
			 newUserEntity.getRole().equals(UserRole.PLAYER)))
		{
			throw new InvalidUserException("Invalid email: " + newUserEntity.getUserId()
					+ " or user name: " + newUserEntity.getUsername());
		}
		this.userDao.save(newUserEntity);
		return this.userConverter.toBoundary(newUserEntity);
	}

	@Override
	@Transactional
	public UserBoundary login(String userSpace, String userEmail) {
		Optional<UserEntity> existingUser = this.userDao.findById(new UserId(userSpace, userEmail).toString());
		if (!existingUser.isPresent())
			throw new UserNotFoundException("user with email: " + userEmail + "does not exist");
		return this.userConverter.toBoundary(existingUser.get());
	}

	@Override
	@Transactional
	public UserBoundary updateUser(UserBoundary update, String userSpace, String userEmail) {
		Optional<UserEntity> existingUser = this.userDao.findById(new UserId(userSpace, userEmail).toString());
		if (!existingUser.isPresent())
		{			
			throw new UserNotFoundException("user with email: " + userEmail + "does not exist");
		}
		UserEntity existingEntity = existingUser.get();
		UserEntity userEntity = this.userConverter.toEntity(update);
		if(!userEntity.getUserId().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$") ||
				userEntity.getUserId() == null || 
				userEntity.getUsername() == null)
			{
			throw new InvalidUserException("Invalid email: " + userEntity.getUserId()
			+ " or user name: " + userEntity.getUsername());
			}
		userEntity.setUserId(existingEntity.getUserId().toString());
		return this.userConverter.toBoundary(this.userDao.save(userEntity));
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		return StreamSupport
				.stream(this.userDao.findAll().spliterator(), false) // Iterable to Stream<UserEntity>,
				.map(entity -> this.userConverter.toBoundary(entity)) // Stream<UserBoundary>
				.collect(Collectors.toList()); // List<UserBoundary>
	}

	@Override
	@Transactional
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		Optional<UserEntity> existingAdmin = this.userDao.findById(new UserId(adminSpace, adminEmail).toString());
		if(!existingAdmin.isPresent())
		{
			throw new AdminNotFoundException("Admin with email: " + existingAdmin + "does not exist");
		}
		UserEntity existingAdminEntity = existingAdmin.get();
		if(!existingAdminEntity.getRole().equals(UserRole.ADMIN))
		{
			throw new InvalidUserException("Invalid role: " + existingAdminEntity.getRole()
			+ " for user: " + existingAdminEntity.getUsername());
		}

		this.userDao.deleteAll();
	}

}
