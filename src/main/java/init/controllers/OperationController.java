package init.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boundaries.OperationBoundary;
import models.operations.ItemIdentifier;


@RestController
public class OperationController {

	
	@RequestMapping(
			method = RequestMethod.POST,
			path = "/dts/operations",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary getOperation(@RequestBody OperationBoundary operation) {
		operation.setOperationId(new ItemIdentifier("default space", "default id"));
		return operation;
	}
}
