package dts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import javax.annotation.PostConstruct;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;


import boundaries.OperationBoundary;
import dts.controllers.AdminController;
import dts.controllers.OperationController;
import models.operations.InvokedBy;
import models.operations.OperationId;
import models.users.UserId;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestOperations {
	
	@Autowired
	private OperationController operationRest;
	
	@Autowired
	private AdminController adminRest;
	
	private int port;
	private String spaceName;
	private String adminSpace,adminEmail;
	private OperationBoundary operationBoundary;
	
	@PostConstruct
	public void init() {
		this.adminSpace="admin@gmail.com";
		this.adminEmail="adminEmail";
		this.operationBoundary=new OperationBoundary();	
	}
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@Value("${spring.application.name:default}")
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	
	@BeforeEach
	public void clearBoudary() {
		this.adminRest.deleteAllOperations(this.adminSpace, this.adminEmail);
		operationBoundary.setCreatedTimestamp(new Date());
		operationBoundary.setOperationId(new OperationId(this.adminSpace, ""));
		operationBoundary.setType("sport");
		operationBoundary.setInvokedBy(new InvokedBy(new UserId(adminSpace, adminEmail)));
	}
	
	@Test
	public void deleteAllOperation() {
		assertThat(this.adminRest.exportAllOperations(this.adminSpace, this.adminEmail, 0, 10)).hasSize(0);
	}
	
	
	@Test
	public void addOneOperation() {
		this.operationRest.invokeOpreation(operationBoundary);
		assertThat(this.adminRest.exportAllOperations(this.adminSpace, this.adminEmail, 0, 10)).hasSize(1);
	}
	
	
	@Test
	public void gelAllOperation() {
		this.operationRest.invokeOpreation(operationBoundary);
		this.operationRest.invokeOpreation(operationBoundary);
		assertThat(this.adminRest.exportAllOperations(this.adminSpace, this.adminEmail, 0, 10)).hasSize(2);
	}
	
	/*
	@Test
	public void deleteTwoAllOperation() {
		this.operationRest.invokeOpreation(operationBoundary);
		operationBoundary.setOperationId(new OperationId(this.adminSpace, "0"));
		this.operationRest.invokeOpreation(operationBoundary);
		this.adminRest.deleteAllItems(this.adminSpace, this.adminEmail);
		assertThat(this.adminRest.exportAllOperations(this.adminSpace, this.adminEmail)).hasSize(0);
	}
	*/
	
	
	

}
