package boundaries;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import models.operations.Item;
import models.operations.ItemIdentifier;

public class OperationBoundary {

	public ItemIdentifier operationId;
	public String type;
	public Item item;
	public Date createdTimestamp;
	public UserBoundary invokedBy;
	public Map<String, Object> operationAttributes;

	public OperationBoundary() {
		this.operationId = new ItemIdentifier("space", "id");
		this.type = "type";
		this.item = new Item(new ItemIdentifier("space", "id"));
		this.createdTimestamp = new Date();
		this.invokedBy = new UserBoundary();
		this.operationAttributes = new HashMap<String, Object>() {
			{
				put("", "");
			}
		};
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
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

	public void setOperationAttributes(Map<String, Object> operationAttributes) {
		this.operationAttributes = operationAttributes;
	}
}
