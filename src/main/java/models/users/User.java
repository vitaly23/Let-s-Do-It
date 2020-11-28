package models.users;

public class User {

	private String space;
	private String email;

	public User() {
		space = "2021a.demo";
		email = "demo@maildomain.com";
	}

	public User(String space, String email) {
		this.space = space;
		this.email = email;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
