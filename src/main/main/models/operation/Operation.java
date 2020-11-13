package main.models.operation;

public class Operation {
	private Identifier id;
	
	public Operation() {
		this.id = new Identifier("123","456");
	}

	public Identifier getId() {
		return id;
	}

	public void setId(Identifier id) {
		this.id = id;
	}
}
