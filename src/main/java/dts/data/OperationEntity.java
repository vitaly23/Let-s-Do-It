package dts.data;

import java.util.Date;
import java.util.Map;

import boundaries.UserBoundary;
import models.operations.ItemIdentifier;

public class OperationEntity {
	public ItemIdentifier operationId;
	public String type;
	public ItemEntity item;
	public Date createdTimestamp;
	public UserBoundary invokedBy; 
	public Map<String, Object> operationAttributes;


	public OperationEntity() {

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
	public UserBoundary getInvokedBy() {
		return invokedBy;
	}
	public void setInvokedBy(UserBoundary invokedBy) {
		this.invokedBy = invokedBy;		
	}
	public Map<String, Object> getOperationAttributes() {
		return operationAttributes;
	}
	public void setOperationAttributes(Map<String, Object> actionAttributes) {
		this.operationAttributes = actionAttributes;
	}

}
