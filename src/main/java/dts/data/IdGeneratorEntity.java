package dts.data;

//import javax.persistence.Entity;
//import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

//@Entity
@Document
public class IdGeneratorEntity {

	private String id;

	public IdGeneratorEntity() {

	}

	@GeneratedValue
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}