package models.operations;

public class Location {
	private double lat;
	private double lng;

	public Location() {
		this.lat = 5.3;
		this.lng = 2.7;
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

}
