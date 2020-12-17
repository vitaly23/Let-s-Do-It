package dts.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import boundaries.ItemBoundary;
import constants.Constants;
import dts.data.ItemEntity;
import dts.utils.Utils;
import models.operations.CreatedBy;
import models.operations.ItemId;
import models.operations.Location;
import models.users.UserId;

@Component
public class ItemConverter {

	private Utils utils;

	@Autowired
	public ItemConverter(Utils utils) {
		this.utils = utils;
	}
	
	public ItemEntity toEntity(ItemBoundary itemBoundary) {
		ItemEntity entity = new ItemEntity();
		if (itemBoundary.getActive() != null) {
			entity.setActive(itemBoundary.getActive());
		}
		entity.setItemId(itemBoundary.getItemId().toString());
		if (itemBoundary.getName() != null) {
			entity.setName(itemBoundary.getName());
		}
		if (itemBoundary.getLocation() != null) {
			entity.setLocation(itemBoundary.getLocation().toString());
		}
		if (itemBoundary.getCreatedBy() != null && itemBoundary.getCreatedBy().getUserId() != null) {
			entity.setCreatedBy(itemBoundary.getCreatedBy().toString());
		}
		if (itemBoundary.getType() != null) {
			entity.setType(itemBoundary.getType());
		}
		if (itemBoundary.getItemAttributes() != null) {
			entity.setItemAttributes(this.utils.toEntity(itemBoundary.getItemAttributes()));
		}
		entity.setCreatedTimestamp(itemBoundary.getCreatedTimestamp());
		return entity;
	}

	public ItemBoundary toBoundary(ItemEntity itemEntity) {
		ItemBoundary itemBoundary = new ItemBoundary();
		if (itemEntity.getActive() != null) {
			itemBoundary.setActive(itemEntity.getActive());
		}
		if (itemEntity.getItemId() != null) {
			String[] args = itemEntity.getItemId().split(Constants.DELIMITER);
			itemBoundary.setItemId(new ItemId(args[0], args[1]));
		}
		if (itemEntity.getName() != null) {
			itemBoundary.setName(itemEntity.getName());
		}
		if (itemEntity.getLocation() != null) {
			String[] args = itemEntity.getLocation().split(Constants.DELIMITER);
			itemBoundary.setLocation(new Location(Double.parseDouble(args[0]), Double.parseDouble(args[1])));
		}
		if (itemEntity.getCreatedBy() != null) {
			String[] args = itemEntity.getCreatedBy().split(Constants.DELIMITER);
			itemBoundary.setCreatedBy(new CreatedBy(new UserId(args[0], args[1])));
		}
		if (itemEntity.getType() != null) {
			itemBoundary.setType(itemEntity.getType());
		}
		if (itemEntity.getItemAttributes() != null) {
			itemBoundary.setItemAttributes(this.utils.toBoundaryAsMap(itemEntity.getItemAttributes()));
		}
		itemBoundary.setCreatedTimestamp(itemEntity.getCreatedTimestamp());
		return itemBoundary;
	}
	
}