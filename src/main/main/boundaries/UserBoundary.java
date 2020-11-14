package main.boundaries;

import main.models.operation.UserId;

public class UserBoundary {
	private UserId userId;
	private RoleBoundary role;
	private String username;
	private String avatar;
	
	public UserBoundary() {
		this.userId= new UserId();
		this.role=new RoleBoundary();
		this.username="Demo User";
		this.avatar="ooOO_()_OOoo";
		
	}
	public UserBoundary(NewUserDetailsBoundary newUser) {
		this.userId.setEmail(newUser.getEmail());
		this.userId.setSpace("2021a.demo");
		this.setUsername(newUser.getUsername());;
		this.role.setRole("");
		this.setAvatar(newUser.getAvatar());;
	}
	
	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	public RoleBoundary getRole() {
		return role;
	}

	public void setRole(RoleBoundary role) {
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
