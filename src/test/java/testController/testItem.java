import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import dts.controllers.AdminController;
import dts.controllers.ItemController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class testItem {
	private int port;
	private String url;
	private ItemController itemRest;
	private AdminController adminRest;
	private String adminSpace,adminEmail;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	
	
	@PostConstruct
	public void init(ItemController itemRest,AdminController adminRest) {
		this.url = "http://localhost:" + port + "";
		this.itemRest = itemRest;
		this.adminRest=adminRest;
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
