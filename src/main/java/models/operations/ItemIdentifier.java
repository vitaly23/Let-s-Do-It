package models.operations;

public class ItemIdentifier {

	public String space;
	public String id;

	public ItemIdentifier() {
		this.space = "default space";
		this.id = "default id";
	}

	public ItemIdentifier(String space, String id) {
		this.space = space;
		this.id = id;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
