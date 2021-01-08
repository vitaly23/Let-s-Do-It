package dts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import boundaries.NewUserDetailsBoundary;
import boundaries.UserBoundary;
import dts.controllers.AdminController;
import dts.controllers.UserController;
import dts.data.UserRole;
import models.users.UserId;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestUser {
	
	@Autowired
	private UserController userRest;
	
	@Autowired
	private AdminController adminRest;
	
	private int port;
	private NewUserDetailsBoundary userBoundary;
	private String spaceName;
	private final String USEREMAIL="ha@gmail.com";
	
	@PostConstruct
	public void init() {
		this.userBoundary=new NewUserDetailsBoundary();
	}
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@Value("${spring.application.name:default}")
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	
	//delete all user before test
	@BeforeEach
	public void clearItem() {
		this.adminRest.deleteAllUsers("", "");
		this.userBoundary.setAvatar("harel");
		this.userBoundary.setRole(UserRole.ADMIN);
		this.userBoundary.setUsername("user1");
		this.userBoundary.setEmail(this.USEREMAIL);
	}
	
	@Test
	public void testClearUser()  {
		assertThat(this.adminRest.exportAllUsers("", "", 0, 10)).hasSize(0);
	}
	
	@Test
	public void addUser() {
		this.userRest.createNewUser(userBoundary);
		assertThat(this.adminRest.exportAllUsers(this.spaceName, this.USEREMAIL, 0, 10)).hasSize(1);
		UserBoundary userBoundaryNew=this.userRest.loginAndRetriveUserDetails(this.spaceName, this.USEREMAIL);
		
		assertEquals(userBoundaryNew.getAvatar(),userBoundary.getAvatar());
		
		assertEquals(userBoundaryNew.getRole(),userBoundary.getRole());
		
		assertEquals(userBoundaryNew.getUsername(),userBoundary.getUsername()); 
		
	}
	@Test
	public void updateUser() {
		//added user
		this.userRest.createNewUser(userBoundary);
		assertThat(this.adminRest.exportAllUsers(this.spaceName, this.USEREMAIL, 0, 10)).hasSize(1);
		UserBoundary userBoundaryNew=this.userRest.loginAndRetriveUserDetails(this.spaceName, this.USEREMAIL);
		
		//update user
		userBoundaryNew.setAvatar("dog");
		userBoundaryNew.setRole(UserRole.MANAGER);
		//userBoundaryNew.setUserId(new UserId("spaceNew", "new"+this.USEREMAIL));
		userBoundaryNew.setUsername("moshe");
		this.userRest.updateExistingUser(this.spaceName, this.USEREMAIL, userBoundaryNew);
		
		//check if equals
		assertEquals(userBoundaryNew, this.userRest.loginAndRetriveUserDetails(this.spaceName,this.USEREMAIL));
	}
	
	@Test
	public void addAllUsers() {
		this.userRest.createNewUser(userBoundary);
		userBoundary.setEmail("other@gmail.com");
		userBoundary.setAvatar("dog");
		userBoundary.setRole(UserRole.MANAGER);
		userBoundary.setUsername("moshe");
		this.userRest.createNewUser(userBoundary);
		assertThat(this.adminRest.exportAllUsers(this.spaceName, this.USEREMAIL, 0, 10)).hasSize(2);
	}
	
	@Test
	public void loginAndRetriveUserDetails() {
		//added user
		this.userRest.createNewUser(userBoundary);
		//login
		UserBoundary userBound=this.userRest.loginAndRetriveUserDetails(this.spaceName, this.USEREMAIL);
		assertEquals(userBound.getAvatar(),userBoundary.getAvatar());
		
		assertEquals(userBound.getRole(),userBoundary.getRole());
		
		assertEquals(userBound.getUsername(),userBoundary.getUsername()); 
	}
		
}
