package models.operations;

public class Item {
	public ItemIdentifier itemId;

	public Item() {
		this.itemId = new ItemIdentifier("", "");
	}

	public Item(ItemIdentifier itemId) {
		this.itemId = itemId;
	}

	public ItemIdentifier getItemId() {
		return itemId;
	}

	public void setItemId(ItemIdentifier itemId) {
		this.itemId = itemId;
	}
}
