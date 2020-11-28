package boundaries;

public class NewUserDetailsBoundary {
	private String email;
	private String role;
	private String username;
	private String avatar;
	
	
	public NewUserDetailsBoundary() {
		this.email="demo@maildomain.com";
		this.role="PLAYER";
		this.username="Demo User";
		this.avatar="ooOO_()_OOoo";
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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
	/*public String toString() {
		return "NewUserDetailsBoundary [email=" + email + ", role=" + role + ", username=" + username + ", avatar="
				+ avatar + "]";
	}*/
}
