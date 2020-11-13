package main.models.operation;

public class UserId {
	
	private String space;
	private String email;
	
	public UserId() {
		this.space = "stub space";
		this.email = "gmail@gmail.com";
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
