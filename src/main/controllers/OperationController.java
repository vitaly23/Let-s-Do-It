package main.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.boundaries.OperationBoundary;
import main.models.operation.Identifier;
import main.models.operation.Operation;

@RestController
public class OperationController {

	
	@RequestMapping(
			method = RequestMethod.POST,
			path = "/dts/operations",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary getOperation(@RequestBody OperationBoundary operation) {
		operation.setOperationId(new Identifier("Kaki", "BaTAhat"));
		return operation;
	}
}
