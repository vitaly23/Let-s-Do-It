package dts.data;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import boundaries.UserBoundary;
import models.operations.ItemIdentifier;
import models.users.UserId;

public class OperationEntity {
	
	private ItemIdentifier operationId;
	private String type;
	private ItemEntity item;
	private Date createdTimestamp;
	private UserId invokedBy; 
	private Map<String, Object> operationAttributes;

	public OperationEntity() {
		this.operationId = new ItemIdentifier("space", "id");
		this.type = "type";
		this.item = new ItemEntity();
		this.createdTimestamp = new Date();
		this.invokedBy = new UserId();
		this.operationAttributes = Collections.synchronizedMap(new HashMap<>());
	}
	public ItemIdentifier getOperationId() {
		return operationId;
	}

	public void setOperationId(ItemIdentifier operationId) {
		this.operationId = operationId;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date date) {
		this.createdTimestamp = date;

	}
	public ItemEntity getItem() {
		return item;
	}
	public void setItem(ItemEntity item) {
		this.item=item;

	}
	public UserId getInvokedBy() {
		return invokedBy;
	}
	public void setInvokedBy(UserId invokedBy) {
		this.invokedBy = invokedBy;		
	}
	public Map<String, Object> getOperationAttributes() {
		return operationAttributes;
	}
	public void setOperationAttributes(Map<String, Object> actionAttributes) {
		this.operationAttributes = actionAttributes;
	}

}
