package boundaries;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import models.operations.InvokedBy;
import models.operations.Item;
import models.operations.ItemId;
import models.operations.OperationId;
import models.users.UserId;

public class OperationBoundary {

	private OperationId operationId;
	private String type;
	private Item item;
	private Date createdTimestamp;
	private InvokedBy invokedBy;
	private Map<String, Object> operationAttributes;

	public OperationBoundary() {
		// default arguments
		this.operationId = new OperationId("my_space", "my_id");
		this.type = "my_type";
		this.item = new Item(new ItemId("my_space", "my_id"));
		this.createdTimestamp = new Date();
		this.invokedBy = new InvokedBy(new UserId("my_space", "my_email"));
		this.operationAttributes = Collections.synchronizedMap(new HashMap<>());
	}

	public OperationId getOperationId() {
		return operationId;
	}

	public void setOperationId(OperationId operationId) {
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

	public InvokedBy getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(InvokedBy invokedBy) {
		this.invokedBy = invokedBy;
	}

	public Map<String, Object> getOperationAttributes() {
		return operationAttributes;
	}

	public void setOperationAttributes(Map<String, Object> operationAttributes) {
		this.operationAttributes = operationAttributes;
	}

}
