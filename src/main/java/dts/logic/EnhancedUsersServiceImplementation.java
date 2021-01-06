package dts.logic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boundaries.UserBoundary;
import dts.converter.UserConverter;
import dts.dao.UserDao;
import dts.data.UserEntity;
import dts.data.UserRole;
import dts.utils.UserHelper;

@Service
public class EnhancedUsersServiceImplementation implements UsersService {

	private UserDao userDao;
	private UserConverter userConverter;
	private UserHelper userHelper;

	@Autowired
	public EnhancedUsersServiceImplementation(UserDao userDao, UserConverter userConverter, UserHelper userHelper) {
		super();
		this.userDao = userDao;
		this.userConverter = userConverter;
		this.userHelper = userHelper;
	}

	@Override
	@Transactional
	public UserBoundary createUser(UserBoundary user) {
		UserEntity newUserEntity = this.userConverter.toEntity(user);
		this.userHelper.ValidateNewUserData(newUserEntity);
		this.userHelper.ValidateNoSuchUser(newUserEntity.getUserId());
		this.userDao.save(newUserEntity);
		return this.userConverter.toBoundary(newUserEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String userSpace, String userEmail) {
		UserEntity existingUser = this.userHelper.getSpecificUser(userSpace, userEmail);
		return this.userConverter.toBoundary(existingUser);
	}

	@Override
	@Transactional
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) {
		UserEntity existingUser = this.userHelper.getSpecificUser(userSpace, userEmail);
		UserEntity updatedUser = this.userConverter.toEntity(update);
		if (update.getUsername() != null)
			existingUser.setUsername(updatedUser.getUsername());
		if (update.getAvatar() != null)
			existingUser.setAvatar(updatedUser.getAvatar());
		if (update.getRole() != null)
			existingUser.setRole(updatedUser.getRole());
		this.userHelper.ValidateUserData(existingUser);
		return this.userConverter.toBoundary(this.userDao.save(existingUser));
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail, int size, int page) {
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(adminSpace, adminEmail, UserRole.ADMIN);
		return StreamSupport
				.stream(this.userDao.findAll(PageRequest.of(page, size, Direction.DESC, "userId")).spliterator(), false)
				.map(entity -> this.userConverter.toBoundary(entity)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(adminSpace, adminEmail, UserRole.ADMIN);
		this.userDao.deleteAll();
	}

}
