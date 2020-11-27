package main.dts.data;


import java.util.Date;

import java.util.Map;


import main.models.operation.ItemIdentifier;

import main.models.operation.Location;

import main.models.operation.UserId;


public class ItemEntity {


	

	private ItemIdentifier itemId;

	private String type;

	private String name;

	private Boolean active;

	private Date createdTimestamp;

	private UserId createdBy;

	private Location location;

	private Map<String, String> itemAttributes;


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


	public UserId getCreatedBy() {

		return createdBy;

	}


	public void setCreatedBy(UserId createdBy) {

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