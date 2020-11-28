package init.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boundaries.NewUserDetailsBoundary;
import boundaries.UserBoundary;

@RestController
public class UserController {

	@RequestMapping(method = RequestMethod.GET, path = "/dts/users/login/{userSpace}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary retriveUserDetails(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail) {

		return new UserBoundary();

	}

	@RequestMapping(path = "/dts/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createNewUser(@RequestBody NewUserDetailsBoundary newUser) {

		return new UserBoundary();
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/dts/users/{userSpace}/{userEmail}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateExistingUser(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, @RequestBody UserBoundary updateUserDetails) {

	}

}
