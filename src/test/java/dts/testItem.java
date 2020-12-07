package dts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import boundaries.ItemBoundary;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import dts.controllers.AdminController;
import dts.controllers.ItemController;
import models.operations.Item;
import models.operations.ItemIdentifier;
import models.operations.Location;
import models.users.UserId;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class testItem {
	
	@Autowired
	private ItemController itemRest;
	
	@Autowired
	private AdminController adminRest;
	
	private int port;
	private String url;
	private String adminSpace,adminEmail;
	private ItemBoundary itemBoundary;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + port + "";
		this.adminSpace="adminSpace";
		this.adminEmail="adminEmail";
		this.itemBoundary=new ItemBoundary();
		
	}
	
	@BeforeEach
	public void clearItem() {
		this.adminRest.deleteAllItems(this.adminSpace, this.adminEmail);
		
		this.itemBoundary.setActive(true);
		this.itemBoundary.setCreatedBy(new UserId("", ""));
		this.itemBoundary.setCreatedTimestamp(new Date());
		Map<String,Object> map=new HashMap<>();
		map.put("key1", "val1");
		map.put("key1", 1);
		this.itemBoundary.setItemAttributes( map);
		this.itemBoundary.setLocation(new Location());
		this.itemBoundary.setType("new type");
		this.itemBoundary.setName("first item");
		this.itemBoundary.setItemId(new ItemIdentifier("", "1"));
		
	}
	
	@Test
	public void testClearItem()  {
		String itemSpace="spaceItem";
		String userEmail="userEmail";
		assertThat(this.itemRest.retrieveAllDigitalItems(itemSpace, userEmail)).hasSize(0);
	}
	
	//check if item was added
	@Test
	public void addedOneItem() {
		this.itemRest.createNewDigitalItem(this.itemBoundary, "", "");
		ItemBoundary existingItem=this.itemRest.retrieveDigitalItem("", "", "2021a.vitalyg1", "1");
		if(!this.itemBoundary.equals(existingItem)) {
			throw new RuntimeException("Error In insert Item");
		}
		
	}
	
	//check if item delete
	@Test
	public void deleteAllItem() {
		this.adminRest.deleteAllItems(this.adminSpace, this.adminEmail);
		assertThat(this.itemRest.retrieveAllDigitalItems("", "")).hasSize(0);
	}
	
	@Test
	public void addAllItems() {
		this.itemRest.createNewDigitalItem(this.itemBoundary, "", "");
		this.itemRest.createNewDigitalItem(this.itemBoundary, "", "");
		assertThat(this.itemRest.retrieveAllDigitalItems("", "")).hasSize(2);	
		
	}

}
