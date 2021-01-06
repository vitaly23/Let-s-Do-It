package dts.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ITEMS")
public class ItemEntity {

	private String itemId;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private String createdBy;
	private double lat;
	private double lng;
	private String itemAttributes;
	private Set<ItemEntity> itemParents;
	private Set<ItemEntity> itemChildren;
	
	public ItemEntity() {
		this.itemParents = new HashSet<>();
		this.itemChildren = new HashSet<>();
	}

	public ItemEntity(String itemId, String type, String name, boolean active, Date createdTimestamp,
			String createdBy, double lat, double lng, String itemAttributes, Set<ItemEntity> itemParents,
			Set<ItemEntity> itemChildren) {
		super();
		this.itemId = itemId;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.lat = lat;
		this.lng = lng;
		this.itemAttributes = itemAttributes;
		this.itemParents = itemParents;
		this.itemChildren = itemChildren;
	}

	@Id
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Lob
	public String getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(String itemAttributes) {
		this.itemAttributes = itemAttributes;
	}
	
	@ManyToMany
	public Set<ItemEntity> getItemParents() {
		return itemParents;
	}
	
	public void setItemParents(Set<ItemEntity> itemParents) {
		this.itemParents = itemParents;
	}
	
	@ManyToMany(mappedBy = "itemParents")
	public Set<ItemEntity> getItemChildren() {
		return itemChildren;
	}

	public void setItemChildren(Set<ItemEntity> itemChildren) {
		this.itemChildren = itemChildren;
	}
	
	public void addChild(ItemEntity child) {	
		this.itemChildren.add(child);
	}
	
	public void addParent(ItemEntity parent) {
		this.itemParents.add(parent);
	}

}