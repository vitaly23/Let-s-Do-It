package init.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boundaries.DigitalItemBoundary;

@RestController
public class DigitalItemController {

	@RequestMapping(method = RequestMethod.POST, path = "/dts/items/{managerSpace}/{managerEmail}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DigitalItemBoundary createNewDigitalItem(
			@RequestBody DigitalItemBoundary item,
			@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail /* //item with no ItemId */) {
		return item;
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateDigitalItem(
			@RequestBody DigitalItemBoundary item,
			@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId) {

	}

	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public DigitalItemBoundary retrieveDigitalItem(
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId) {
		return new DigitalItemBoundary();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public DigitalItemBoundary[] retrieveAllDigitalItems(
			@PathVariable("userSpace") String itemSpace,
			@PathVariable("userEmail") String userEmail) {
		return new DigitalItemBoundary[] { new DigitalItemBoundary(), new DigitalItemBoundary() };
	}
}
