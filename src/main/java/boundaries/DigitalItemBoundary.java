package boundaries;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import models.operations.ItemIdentifier;
import models.operations.Location;
import models.users.User;

public class DigitalItemBoundary {

	private ItemIdentifier itemId;
	private String type;
	private String name;
	private Boolean active;
	private Date createdTimestamp;
	private User createdBy;
	private Location location;
	private Map<String, String> itemAttributes;

	public DigitalItemBoundary() {

		this.itemId = new ItemIdentifier();
		this.type = "new_type";
		this.name = "name";
		this.active = true;
		this.createdTimestamp = new Date();
		this.createdBy = new User();
		this.location = new Location();
		this.itemAttributes = new HashMap<>();
	}

	public ItemIdentifier getItemId() {
		return itemId;
	}

	public void setItemId(ItemIdentifier itemId) {
		this.itemId = itemId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Map<String, String> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(Map<String, String> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

}
