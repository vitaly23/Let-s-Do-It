package dts.logic;

import java.util.List;

import boundaries.UserBoundary;

public interface UsersService {

	public UserBoundary createUser(UserBoundary user);

	public UserBoundary login(String userSpace, String userEmail);

	public UserBoundary updateUser(UserBoundary update, String userSpace, String userEmail);

	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail);

	public void deleteAllUsers(String adminSpace, String adminEmail);

}