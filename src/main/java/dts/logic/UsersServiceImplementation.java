package dts.logic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boundaries.UserBoundary;
import constants.Constants;
import dts.converter.UserConverter;
import dts.dao.UserDao;
import dts.data.UserEntity;
import exceptions.UserNotFoundException;

@Service
public class UsersServiceImplementation implements UsersService {

	private UserDao userDao;
	private UserConverter userConverter;

	@Autowired
	public UsersServiceImplementation(UserDao userDao, UserConverter userConverter) {
		super();
		this.userDao = userDao;
		this.userConverter = userConverter;
	}

	@Override
	@Transactional
	public UserBoundary createUser(UserBoundary user) {
		UserEntity newUserEntity = this.userConverter.toEntity(user);
		this.userDao.save(newUserEntity);
		return this.userConverter.toBoundary(newUserEntity);
	}

	@Override
	@Transactional
	public UserBoundary login(String userSpace, String userEmail) {
		Optional<UserEntity> existingUser = this.userDao.findById(userSpace + Constants.DELIMITER + userEmail);
		if (!existingUser.isPresent())
			throw new UserNotFoundException("user with email: " + userEmail + "does not exist");
		return this.userConverter.toBoundary(existingUser.get());
	}

	@Override
	@Transactional
	public UserBoundary updateUser(UserBoundary update, String userSpace, String userEmail) {
		Optional<UserEntity> existingUser = this.userDao.findById(userSpace + Constants.DELIMITER + userEmail);
		if (!existingUser.isPresent())
			throw new UserNotFoundException("user with email: " + userEmail + "does not exist");
		UserEntity existingEntity = existingUser.get();
		UserEntity userEntity = this.userConverter.toEntity(update);
		userEntity.setUserId(existingEntity.getUserId().toString());
		return this.userConverter.toBoundary(this.userDao.save(userEntity));
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		return StreamSupport.stream(this.userDao.findAll().spliterator(), false) // Iterable to Stream<ItemEntity>,
				.map(entity -> this.userConverter.toBoundary(entity)) // Stream<ItemBoundary>
				.collect(Collectors.toList()); // List<ItemBoundary>
	}

	@Override
	@Transactional
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		this.userDao.deleteAll();
	}

}
