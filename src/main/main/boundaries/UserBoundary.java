package main.boundaries;

import main.models.operation.UserId;

public class UserBoundary {
	private UserId userId;
	private String role;
	private String username;
	private String avatar;
	
	public UserBoundary() {
		this.userId= new UserId();
		this.role="PLAYER";
		this.username="Demo User";
		this.avatar="ooOO_()_OOoo";
		
	}
	/*public UserBoundary(NewUserDetailsBoundary newUser) {
		this.userId.setEmail(newUser.getEmail());
		this.userId.setSpace("2021a.demo");
		this.setUsername(newUser.getUsername());;
		//this.role.setRole(newUser.getRole());
		this.setAvatar(newUser.getAvatar());;
	}
	*/
	public void setUserId(UserId userId) {
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
	public String toString() {
		return "UserBoundary [userId=" + userId + ", role=" + role + ", username=" + username + ", avatar="
				+ avatar + "]";
	}
}
