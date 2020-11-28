package boundaries;

import models.users.User;

public class UserBoundary {
	private User userId;
	private String role;
	private String username;
	private String avatar;

	public UserBoundary() {
		this.userId = new User();
		this.role = "PLAYER";
		this.username = "Demo User";
		this.avatar = "ooOO_()_OOoo";

	}

	/*
	 * public UserBoundary(NewUserDetailsBoundary newUser) {
	 * this.userId.setEmail(newUser.getEmail()); this.userId.setSpace("2021a.demo");
	 * this.setUsername(newUser.getUsername());;
	 * //this.role.setRole(newUser.getRole()); this.setAvatar(newUser.getAvatar());;
	 * }
	 */
	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
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
	

	public User getUserId() {
		return userId;
	}

	public String toString() {
		return "UserBoundary [userId=" + userId + ", role=" + role + ", username=" + username + ", avatar=" + avatar
				+ "]";
	}
}
