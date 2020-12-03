package init.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boundaries.ItemBoundary;
import dts.logic.ItemService;

@RestController
public class DigitalItemController {

	private ItemService itemService;

	@Autowired
	public DigitalItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/dts/items/{managerSpace}/{managerEmail}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary createNewDigitalItem(@RequestBody ItemBoundary item,
			@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail /* //item with no ItemId */) {

		return this.itemService.create(managerSpace, managerEmail, item);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateDigitalItem(@RequestBody ItemBoundary item,
			@PathVariable("managerSpace") String managerSpace, @PathVariable("managerEmail") String managerEmail,
			@PathVariable("itemSpace") String itemSpace, @PathVariable("itemId") String itemId) {
		this.itemService.update(managerSpace, managerEmail, itemSpace, itemId, item);

	}

	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary retrieveDigitalItem(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId) {
		return this.itemService.getSpecificItem(userSpace, userEmail, itemSpace, itemId);
		
	}

	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] retrieveAllDigitalItems(@PathVariable("userSpace") String itemSpace,
			@PathVariable("userEmail") String userEmail) {
		return this.retrieveAllDigitalItems(itemSpace, userEmail);
	}
}
