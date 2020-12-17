package dts.converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import boundaries.NewUserDetailsBoundary;
import boundaries.UserBoundary;
import constants.Constants;
import dts.data.UserEntity;
import dts.data.UserRole;
import models.users.UserId;

@Component
public class UserConverter {
	
	String spaceName;
	
	@Value("${spring.application.name:default_space_name}")
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	
	public UserBoundary toBoundary(NewUserDetailsBoundary user) {
		UserBoundary boundary = new UserBoundary();
		if (user.getEmail() != null) {
			boundary.setUserId(new UserId(this.spaceName, user.getEmail()));
		}
		if (user.getRole() != null) {
			boundary.setRole(UserRole.valueOf(user.getRole().name()));
		}
		if (user.getUsername() != null) {
			boundary.setUsername(user.getUsername());
		}
		if (user.getAvatar() != null) {
			boundary.setAvatar(user.getAvatar());
		}
		return boundary;
	}

	public UserEntity toEntity(UserBoundary userBoundary) {
		UserEntity entity = new UserEntity();
		if (userBoundary.getAvatar() != null) {
			entity.setAvatar(userBoundary.getAvatar());
		}
		if (userBoundary.getRole() != null) {
			entity.setRole(UserRole.valueOf(userBoundary.getRole().name()));
		}
		if (userBoundary.getUserId() != null) {
			entity.setUserId(userBoundary.getUserId().toString());
		}
		if (userBoundary.getUsername() != null) {
			entity.setUsername(userBoundary.getUsername());
		}
		return entity;
	}

	public UserBoundary toBoundary(UserEntity userEntity) {
		UserBoundary boundary = new UserBoundary();
		if (userEntity.getAvatar() != null) {
			boundary.setAvatar(userEntity.getAvatar());
		}
		if (userEntity.getRole() != null) {
			boundary.setRole(UserRole.valueOf(userEntity.getRole().name()));
		}
		if (userEntity.getUserId() != null) {
			String[] args = userEntity.getUserId().split(Constants.DELIMITER);
			boundary.setUserId(new UserId(args[0], args[1]));
		}
		if (userEntity.getUsername() != null) {
			boundary.setUsername(userEntity.getUsername());
		}
		return boundary;
	}

}
