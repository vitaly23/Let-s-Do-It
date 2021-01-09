package dts.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dts.dao.ItemDao;
import dts.data.ItemEntity;

import exceptions.InvalidItemException;
import exceptions.ItemNotFoundException;

import models.operations.ItemId;

@Component
public class ItemHelper {

	private ItemDao itemDao;

	@Autowired
	public ItemHelper(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public void validateItemFound(Optional<ItemEntity> itemEntity, String itemId) {
		if (!itemEntity.isPresent()) {
			throw new ItemNotFoundException("Item with id: " + itemId + " does not exist");
		}
	}

	public void validateItemData(ItemEntity itemEntity) {
		if (itemEntity.getType() == null || itemEntity.getType().isEmpty() || itemEntity.getName() == null
				|| itemEntity.getName().isEmpty()) {
			throw new InvalidItemException("Item type or name can not be null or empty");
		}
	}

	@Transactional(readOnly = true)
	public ItemEntity getSpecificItem(String itemSpace, String itemId) {
		Optional<ItemEntity> optionalItem = this.itemDao.findById(new ItemId(itemSpace, itemId).toString());
		this.validateItemFound(optionalItem, itemId);
		return optionalItem.get();
	}

	@Transactional(readOnly = true)
	public ItemEntity getActiveSpecificItem(String itemSpace, String itemId) {
		Optional<ItemEntity> optionalItem = this.itemDao
				.findByActiveTrueAndItemId(new ItemId(itemSpace, itemId).toString());
		this.validateItemFound(optionalItem, itemId);
		return optionalItem.get();
	}

}
