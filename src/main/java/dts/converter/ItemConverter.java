package dts.converter;

import org.springframework.stereotype.Component;
import boundaries.ItemBoundary;
import dts.data.ItemEntity;

@Component
public class ItemConverter {

	public ItemConverter() {
	}

	public ItemEntity toEntity(ItemBoundary itemBoundary) {

		ItemEntity entity = new ItemEntity();

		if (itemBoundary.getActive() != null) {
			entity.setActive(itemBoundary.getActive());
		}

		if (itemBoundary.getItemId() != null) {
			entity.setItemId(itemBoundary.getItemId());
		}

		if (itemBoundary.getName() != null) {
			entity.setName(itemBoundary.getName());
		}

		if (itemBoundary.getLocation() != null) {
			entity.setLocation(itemBoundary.getLocation());
		}

		if (itemBoundary.getCreatedBy() != null) {
			entity.setCreatedBy(itemBoundary.getCreatedBy());
		}

		if (itemBoundary.getType() != null) {
			entity.setType(itemBoundary.getType());
		}

		if (itemBoundary.getItemAttributes() != null) {
			entity.setItemAttributes(itemBoundary.getItemAttributes());
		}

		if (itemBoundary.getCreatedTimestamp() != null) {
			entity.setCreatedTimestamp(itemBoundary.getCreatedTimestamp());
		}
		
		return entity;
	}

	public ItemBoundary toBoundary(ItemEntity itemEntity) {

		ItemBoundary itemBoundary = new ItemBoundary();

		if (itemEntity.getActive() != null) {
			itemBoundary.setActive(itemEntity.getActive());
		}

		if (itemEntity.getItemId() != null) {
			itemBoundary.setItemId(itemEntity.getItemId());
		}

		if (itemEntity.getName() != null) {
			itemBoundary.setName(itemEntity.getName());
		}

		if (itemEntity.getLocation() != null) {
			itemBoundary.setLocation(itemEntity.getLocation());
		}

		if (itemEntity.getCreatedBy() != null) {
			itemBoundary.setCreatedBy(itemEntity.getCreatedBy());
		}

		if (itemEntity.getType() != null) {
			itemBoundary.setType(itemEntity.getType());
		}
		if (itemEntity.getItemAttributes() != null) {
			itemBoundary.setItemAttributes(itemEntity.getItemAttributes());
		}
		if (itemEntity.getCreatedTimestamp() != null) {
			itemBoundary.setCreatedTimestamp(itemEntity.getCreatedTimestamp());
		}

		return itemBoundary;
	}
	
	
}