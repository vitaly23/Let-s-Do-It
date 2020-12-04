package dts.data;

import models.users.User;

public class UserEntity {
	
	private User userId;
	private UserRole role;
	private String username;
	private String avatar;
		
	public UserEntity() {
		this.userId = new User();
		this.role = UserRole.PLAYER;
		this.username = "Demo User";
		this.avatar = "ooOO_()_OOoo";
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
}
