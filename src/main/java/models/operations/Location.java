package models.operations;

import constants.Constants;

public class Location {
	private double lat;
	private double lng;

	public Location() {

	}
	
	public Location(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public double getLan() {
		return lat;
	}

	public void setLan(double lan) {
		this.lat = lan;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return this.lat + Constants.DELIMITER + this.lng;
	}

}
