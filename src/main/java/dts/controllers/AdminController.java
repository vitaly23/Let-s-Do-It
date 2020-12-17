package dts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boundaries.OperationBoundary;
import boundaries.UserBoundary;
import dts.logic.ItemsService;
import dts.logic.OperationsService;
import dts.logic.UsersService;

@RestController
public class AdminController {

	private ItemsService itemService;
	private UsersService usersService;
	private OperationsService operationService;
	
	
	@Autowired
	public AdminController(ItemsService itemService, UsersService usersService, OperationsService operationService) {
		this.itemService = itemService;
		this.usersService = usersService;
		this.operationService = operationService;
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/dts/admin/users/{adminSpace}/{adminEmail}")
	public void deleteAllUsers(
			@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
			this.usersService.deleteAllUsers(adminSpace, adminEmail);
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/dts/admin/items/{adminSpace}/{adminEmail}")
	public void deleteAllItems(
			@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
			this.itemService.deleteAll(adminSpace, adminEmail);
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/dts/admin/operations/{adminSpace}/{adminEmail}")
	public void deleteAllOperations(
			@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
			this.operationService.deleteAllActions(adminSpace, adminEmail);
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/dts/admin/users/{adminSpace}/{adminEmail}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers(
			@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		List<UserBoundary> listUser = this.usersService.getAllUsers(adminSpace, adminEmail);
		UserBoundary[] array = new UserBoundary[listUser.size()];
		listUser.toArray(array);
		return array;
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/dts/admin/operations/{adminSpace}/{adminEmail}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary[] exportAllOperations(
			@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		List<OperationBoundary> listBoundary = this.operationService.getAllOperations(adminSpace, adminEmail);
		OperationBoundary[] array = new OperationBoundary[listBoundary.size()];
		listBoundary.toArray(array);
		return array;
	}

}
