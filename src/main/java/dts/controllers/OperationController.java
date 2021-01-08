package dts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boundaries.OperationBoundary;
import dts.logic.OperationsService;

@RestController
public class OperationController {

	private OperationsService operationsService;

	@Autowired
	public OperationController(OperationsService operationsService) {
		this.operationsService = operationsService;
	}

	@RequestMapping(
			method = RequestMethod.POST, 
			path = "/dts/operations", 
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object invokeOpreation(@RequestBody OperationBoundary operation) {
		return this.operationsService.invokeOpreation(operation);
	}

}
