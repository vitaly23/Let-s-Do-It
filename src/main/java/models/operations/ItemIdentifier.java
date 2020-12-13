package models.operations;

public class ItemIdentifier {

	private String space;
	private String id;

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((space == null) ? 0 : space.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemIdentifier other = (ItemIdentifier) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (space == null) {
			if (other.space != null)
				return false;
		} else if (!space.equals(other.space))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemIdentifier [space=" + space + ", id=" + id + "]";
	}
	
}
