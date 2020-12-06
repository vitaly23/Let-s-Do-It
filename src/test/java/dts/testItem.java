package dts;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import dts.controllers.AdminController;
import dts.controllers.ItemController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class testItem {
	
	@Autowired
	private ItemController itemRest;
	
	@Autowired
	private AdminController adminRest;
	
	private int port;
	private String url;
	private String adminSpace,adminEmail;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + port + "";
		this.adminSpace="adminSpace";
		this.adminEmail="adminEmail";
		
	}
	
	@BeforeEach
	public void clearItem() {
		this.adminRest.deleteAllItems(this.adminSpace, this.adminEmail);
		
	}
	
	@Test
	public void testClearItem()  {
		String itemSpace="spaceItem";
		String userEmail="userEmail";
		assertThat(this.itemRest.retrieveAllDigitalItems(itemSpace, userEmail)).hasSize(0);
	}

}
