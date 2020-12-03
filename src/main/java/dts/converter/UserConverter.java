package dts.converter;

import org.springframework.stereotype.Component;

import boundaries.UserBoundary;
import dts.data.UserEntity;

@Component
public class UserConverter {

	public UserBoundary toBoundary(UserEntity user) {
		UserBoundary boundary = new UserBoundary();

		if (user.getAvatar() != null)
			boundary.setAvatar(user.getAvatar());

		if (user.getRole() != null)
			boundary.setRole(user.getRole());

		if (user.getUserId() != null)
			boundary.setUserId(user.getUserId());

		if (user.getUsername() != null)
			boundary.setUsername(user.getUsername());

		return boundary;

	}

	public UserEntity toEntity(UserBoundary boundary) {

		UserEntity entity = new UserEntity();

		if (boundary.getAvatar() != null)
			entity.setAvatar(boundary.getAvatar());

		if (boundary.getRole() != null)
			entity.setRole(boundary.getRole());

		if (boundary.getUserId() != null)
			entity.setUserId(boundary.getUserId());

		if (boundary.getUsername() != null)
			entity.setUsername(boundary.getUsername());

		return entity;

	}
}
