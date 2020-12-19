package dts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boundaries.ItemBoundary;
import dts.logic.EnhancedItemsService;
import models.operations.ItemId;

@RestController
public class ItemController {

	private EnhancedItemsService itemService;

	@Autowired
	public ItemController(EnhancedItemsService itemService) {
		this.itemService = itemService;
	}

	@RequestMapping(
			method = RequestMethod.POST, 
			path = "/dts/items/{managerSpace}/{managerEmail}", 
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary createNewDigitalItem(
			@RequestBody ItemBoundary item,
			@PathVariable("managerSpace") String managerSpace, 
			@PathVariable("managerEmail") String managerEmail) {
		return this.itemService.create(managerSpace, managerEmail, item);
	}

	@RequestMapping(
			method = RequestMethod.PUT, 
			path = "/dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}", 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateDigitalItem(
			@RequestBody ItemBoundary item, 
			@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail, 
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId) {
		this.itemService.update(managerSpace, managerEmail, itemSpace, itemId, item);
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary retrieveDigitalItem(
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, 
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId) {
		return this.itemService.getSpecificItem(userSpace, userEmail, itemSpace, itemId);
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/dts/items/{userSpace}/{userEmail}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] retrieveAllDigitalItems(
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail) {
		List<ItemBoundary> listItem = this.itemService.getAll(userSpace, userEmail);
		ItemBoundary[] array = new ItemBoundary[listItem.size()];
		listItem.toArray(array);
		return array;
	}
	
	@RequestMapping(
			method = RequestMethod.PUT,
			path = "/dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}/children",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void bindItemAsChild (
			@RequestBody ItemId item,
			@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId) {
		this.itemService.bindChild(managerSpace, managerEmail, itemSpace, itemId, item);
	}
	
	@RequestMapping(method = RequestMethod.GET,
			path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/children",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getItemChildren (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId) {
		List<ItemBoundary> listItem = this.itemService.getAllChildren(userSpace, userEmail, itemSpace, itemId);
		ItemBoundary[] array = new ItemBoundary[listItem.size()];
		listItem.toArray(array);
		return array;
	}
	
	@RequestMapping(method = RequestMethod.GET,
			path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/parents",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getItemParents (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId){
		List<ItemBoundary> listItem = this.itemService.getParents(userSpace, userEmail, itemSpace, itemId);
		ItemBoundary[] array = new ItemBoundary[listItem.size()];
		listItem.toArray(array);
		return array;
	}
	
}
