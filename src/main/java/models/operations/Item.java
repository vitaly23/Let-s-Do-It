package models.operations;

public class Item {
	
	private ItemId itemId;

	public Item() {

	}

	public Item(ItemId itemId) {
		this.itemId = itemId;
	}

	public ItemId getItemId() {
		return itemId;
	}

	public void setItemId(ItemId itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return this.itemId.toString();
	}

}
