package models.operations;

public class Operation {
	private ItemIdentifier id;

	public Operation() {
		this.id = new ItemIdentifier("123", "456");
	}

	public ItemIdentifier getId() {
		return id;
	}

	public void setId(ItemIdentifier id) {
		this.id = id;
	}
}
