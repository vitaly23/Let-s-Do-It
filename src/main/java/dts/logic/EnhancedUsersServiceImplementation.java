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
import dts.utils.ValidationService;
import models.users.UserId;

@Service
public class EnhancedUsersServiceImplementation implements UsersService {

	private UserDao userDao;
	private UserConverter userConverter;
	private ValidationService validationService; 

	@Autowired
	public EnhancedUsersServiceImplementation(UserDao userDao, 
			UserConverter userConverter,
			ValidationService validationService) {
		super();
		this.userDao = userDao;
		this.userConverter = userConverter;
		this.validationService = validationService;
	}

	@Override
	@Transactional
	public UserBoundary createUser(UserBoundary user) {
		UserEntity newUserEntity = this.userConverter.toEntity(user);
		Optional<UserEntity> existingUser = this.userDao.findById(newUserEntity.getUserId());
		this.validationService.ValidateNotSuchUser(newUserEntity, existingUser);
		this.validationService.ValidateUserData(newUserEntity);
		this.userDao.save(newUserEntity);
		return this.userConverter.toBoundary(newUserEntity);
	}

	@Override
	@Transactional
	public UserBoundary login(String userSpace, String userEmail) {
		Optional<UserEntity> existingUser = this.userDao.findById(new UserId(userSpace, userEmail).toString());
		this.validationService.ValidateUserFound(existingUser, userEmail);
		return this.userConverter.toBoundary(existingUser.get());
	}

	@Override
	@Transactional
	public UserBoundary updateUser(UserBoundary update, String userSpace, String userEmail) {
		Optional<UserEntity> existingUser = this.userDao.findById(new UserId(userSpace, userEmail).toString());
		this.validationService.ValidateUserFound(existingUser, userEmail);
		UserEntity existingEntity = existingUser.get();
		UserEntity userEntity = this.userConverter.toEntity(update);
		userEntity.setUserId(existingEntity.getUserId().toString());
		this.validationService.ValidateUserData(userEntity);
		return this.userConverter.toBoundary(this.userDao.save(userEntity));
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		Optional<UserEntity> admin = this.userDao.findById(new UserId(adminSpace, adminEmail).toString());
		this.validationService.ValidateUserFound(admin, adminEmail);
		this.validationService.ValidateRole(admin, UserRole.ADMIN);	
		return StreamSupport
				.stream(this.userDao.findAll().spliterator(), false) // Iterable to Stream<UserEntity>,
				.map(entity -> this.userConverter.toBoundary(entity)) // Stream<UserBoundary>
				.collect(Collectors.toList()); // List<UserBoundary>
	}

	@Override
	@Transactional
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		Optional<UserEntity> existingAdmin = this.userDao.findById(new UserId(adminSpace, adminEmail).toString());
		this.validationService.ValidateUserFound(existingAdmin, adminEmail);
		this.validationService.ValidateRole(existingAdmin, UserRole.ADMIN);	

		this.userDao.deleteAll();
	}

}
