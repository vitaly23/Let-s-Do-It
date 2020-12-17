package dts.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Utils {

	private ObjectMapper jackson;

	public Utils() {
		this.jackson = new ObjectMapper();
	}

	// use JACKSON to store JSON String in the database
	public String toEntity(Map<String, Object> moreDetails) {
		// marshalling Java Object->JSON:
		if (moreDetails != null) {
			try {
				return this.jackson.writeValueAsString(moreDetails);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return "{}";
		}
	}

	// use JACKSON to store JSON String in the database
	public Map<String, Object> toBoundaryAsMap(String moreDetails) {
		// unmarshalling: JSON > Object
		if (moreDetails != null) {
			try {
				return this.jackson.readValue(moreDetails, Map.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return new HashMap<>();
		}
	}

	public ObjectMapper getJackson() {
		return jackson;
	}

	public void setJackson(ObjectMapper jackson) {
		this.jackson = jackson;
	}

}
