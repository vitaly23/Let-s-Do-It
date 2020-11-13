package main.models.operation;

public class Item {
	public Identifier itemId;
	
	public Item() {
		this.itemId = new Identifier("","");
	}
	public Item(Identifier itemId) {
		this.itemId = itemId;
	}

	public Identifier getItemId() {
		return itemId;
	}

	public void setItemId(Identifier itemId) {
		this.itemId = itemId;
	}
}
