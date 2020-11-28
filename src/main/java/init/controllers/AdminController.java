package init.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boundaries.OperationBoundary;
import boundaries.UserBoundary;


@RestController
public class AdminController {
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/dts/admin/users/{adminSpace}/{adminEmail}")
	public void deleteAllUsers(@PathVariable("adminSpace") String adminSpace,@PathVariable("adminEmail") String adminEmail) {
		// TODO delete all users from DB
	}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/dts/admin/items/{adminSpace}/{adminEmail}")
	public void deleteAllItems(@PathVariable("adminSpace") String adminSpace,@PathVariable("adminEmail") String adminEmail) {
		// TODO delete all items from DB
	}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/dts/admin/operations/{adminSpace}/{adminEmail}")
	public void deleteAllOperations(@PathVariable("adminSpace") String adminSpace,@PathVariable("adminEmail") String adminEmail) {
		// TODO delete all operations from DB
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/dts/admin/users/{adminSpace}/{adminEmail}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers(@PathVariable("adminSpace") String adminSpace,@PathVariable("adminEmail") String adminEmail) {
			
		return new UserBoundary[] {new UserBoundary(), new UserBoundary()};
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/dts/admin/operations/{adminSpace}/{adminEmail}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary[] exportAllOperations(@PathVariable("adminSpace") String adminSpace,@PathVariable("adminEmail") String adminEmail) {
		return new OperationBoundary[] {new OperationBoundary(), new OperationBoundary()};
	}
	
}
