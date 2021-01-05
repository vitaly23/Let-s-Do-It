package dts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boundaries.ItemBoundary;
import dts.logic.EnhancedItemsService;
import models.operations.ItemId;

@RestController
public class ItemController {

	private EnhancedItemsService itemsService;

	@Autowired
	public ItemController(EnhancedItemsService itemsService) {
		this.itemsService = itemsService;
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
		return this.itemsService.create(managerSpace, managerEmail, item);
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
		this.itemsService.update(managerSpace, managerEmail, itemSpace, itemId, item);
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
		return this.itemsService.getSpecificItem(userSpace, userEmail, itemSpace, itemId);
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/dts/items/{userSpace}/{userEmail}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] retrieveAllDigitalItems(
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		List<ItemBoundary> listItem = this.itemsService.getAll(userSpace, userEmail, size, page);
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
		this.itemsService.bindChild(managerSpace, managerEmail, itemSpace, itemId, item);
	}
	
	@RequestMapping(method = RequestMethod.GET,
			path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/children",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getItemChildren (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		List<ItemBoundary> listItem = this.itemsService.getAllChildren(userSpace, userEmail, itemSpace, itemId, size, page);
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
			@PathVariable("itemId") String itemId,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page){
		List<ItemBoundary> listItem = this.itemsService.getParents(userSpace, userEmail, itemSpace, itemId, size, page);
		ItemBoundary[] array = new ItemBoundary[listItem.size()];
		listItem.toArray(array);
		return array;
	}
	
	@RequestMapping(method = RequestMethod.GET,
			path = "/dts/items/{userSpace}/{userEmail}/search/byNamePattern/{namePattern}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getItemsByNamePattern (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("namePattern") String namePattern,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page){
		List<ItemBoundary> listItem = this.itemsService.getAllByNamePattern(userSpace, userEmail, namePattern, size, page);
		ItemBoundary[] array = new ItemBoundary[listItem.size()];
		listItem.toArray(array);
		return array;
	}
	
	@RequestMapping(method = RequestMethod.GET,
			path = "/dts/items/{userSpace}/{userEmail}/search/byType/{type}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getItemsByType (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("type") String type,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page){
		List<ItemBoundary> listItem = this.itemsService.getAllByType(userSpace, userEmail, type, size, page);
		ItemBoundary[] array = new ItemBoundary[listItem.size()];
		listItem.toArray(array);
		return array;
	}
	
	@RequestMapping(method = RequestMethod.GET,
			path = "/dts/items/{userSpace}/{userEmail}/search/near/{lat}/{lng}/{distance}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getItemsByLocation (
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("lat") double lat,
			@PathVariable("lng") double lng,
			@PathVariable("distance") double distance,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page){
		List<ItemBoundary> listItem = this.itemsService.getAllByLocation(userSpace, userEmail, lat, lng, distance, size, page);
		ItemBoundary[] array = new ItemBoundary[listItem.size()];
		listItem.toArray(array);
		return array;
	}
	
}
