package main.dts.logic;


import java.util.List;


import main.boundaries.UserBoundary;


public interface UsersService { 

	

	public UserBoundary createUser(UserBoundary UserBoundary);

	

	public UserBoundary login(String UserSpace,String userEmail);

	

	public UserBoundary updateUser(UserBoundary UserBoundary,String UserSpace,String userEmail);

	

	public List<UserBoundary> getAllUsers(String adminSpace,String adminEmail);

	

	public void deleteAllUsers(String adminSpace,String adminEmail);


}