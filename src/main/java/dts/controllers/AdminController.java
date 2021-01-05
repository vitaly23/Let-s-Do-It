package dts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boundaries.OperationBoundary;
import boundaries.UserBoundary;
import dts.logic.EnhancedItemsService;
import dts.logic.OperationsService;
import dts.logic.UsersService;

@RestController
public class AdminController {

	private EnhancedItemsService itemsService;
	private UsersService usersService;
	private OperationsService operationsService;

	@Autowired
	public AdminController(EnhancedItemsService itemsService, UsersService usersService,
			OperationsService operationsService) {
		this.itemsService = itemsService;
		this.usersService = usersService;
		this.operationsService = operationsService;
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
		this.itemsService.deleteAll(adminSpace, adminEmail);
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/dts/admin/operations/{adminSpace}/{adminEmail}")
	public void deleteAllOperations(
			@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		this.operationsService.deleteAllActions(adminSpace, adminEmail);
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/dts/admin/users/{adminSpace}/{adminEmail}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers(
			@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		List<UserBoundary> listUser = this.usersService.getAllUsers(adminSpace, adminEmail, size, page);
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
			@PathVariable("adminEmail") String adminEmail,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		List<OperationBoundary> listBoundary = this.operationsService.getAllOperations(adminSpace, adminEmail, size,
				page);
		OperationBoundary[] array = new OperationBoundary[listBoundary.size()];
		listBoundary.toArray(array);
		return array;
	}

}
