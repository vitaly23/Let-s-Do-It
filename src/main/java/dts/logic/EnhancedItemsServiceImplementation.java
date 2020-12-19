package dts.logic;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boundaries.ItemBoundary;
import dts.converter.ItemConverter;
import dts.dao.IdGeneratorDao;
import dts.dao.ItemDao;
import dts.dao.UserDao;
import dts.data.IdGeneratorEntity;
import dts.data.ItemEntity;
import dts.data.UserEntity;
import dts.data.UserRole;
import exceptions.AdminNotFoundException;
import exceptions.InvalidItemTypeException;
import exceptions.InvalidUserException;
import exceptions.ItemNotFoundException;
import models.operations.CreatedBy;
import models.operations.ItemId;
import models.users.UserId;

@Service
public class EnhancedItemsServiceImplementation implements EnhancedItemsService {

	private ItemDao itemDao;
	private ItemConverter itemConverter;
	private IdGeneratorDao idGeneratorDao;
	private UserDao userDao;


	@Autowired
	public EnhancedItemsServiceImplementation(ItemConverter itemConvertor, ItemDao itemDao,
			IdGeneratorDao idGeneratorDao,
			UserDao userDao) {
		this.itemConverter = itemConvertor;
		this.itemDao = itemDao;
		this.idGeneratorDao = idGeneratorDao;
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public ItemBoundary create(String managerSpace, String managerEmail, ItemBoundary newItem) {
		ItemEntity newItemEntity = this.itemConverter.toEntity(newItem);
		if(newItemEntity.getType().isEmpty() ||
		   newItemEntity.getType() == null)
		{
			throw new InvalidItemTypeException("Invalid type: " + 
					newItemEntity.getType() + " for item: " + 
					newItemEntity.getName());
		}
		IdGeneratorEntity idGeneratorEntity = new IdGeneratorEntity();
		idGeneratorEntity = this.idGeneratorDao.save(idGeneratorEntity);
		Long numricId = idGeneratorEntity.getId();
		this.idGeneratorDao.deleteById(numricId);
		String strId = "" + numricId;
		newItemEntity.setCreatedTimestamp(new Date());
		newItemEntity.setItemId(new ItemId(managerSpace, strId).toString());
		newItemEntity.setCreatedBy(new CreatedBy(new UserId(managerSpace, managerEmail)).toString());
		itemDao.save(newItemEntity);
		return this.itemConverter.toBoundary(newItemEntity);
	}

	@Override
	@Transactional
	public ItemBoundary update(String managerSpace, String managerEmail, String itemSpace, String itemId,
			ItemBoundary update) {
		Optional<ItemEntity> existingItem = this.itemDao.findById(new ItemId(itemSpace, itemId).toString());
		if (!existingItem.isPresent() || 
				itemId.isEmpty() ||
				itemId == null ||
				itemSpace.isEmpty() ||
				itemSpace == null)
		{			
			throw new ItemNotFoundException("item with id: " + itemId + "and space: " + itemSpace + " does not exist");
		}
		ItemEntity existingEntity = existingItem.get();
		ItemEntity itemEntity = this.itemConverter.toEntity(update);
		itemEntity.setCreatedTimestamp(existingEntity.getCreatedTimestamp());
		itemEntity.setItemId(existingEntity.getItemId());
		itemEntity.setCreatedBy(new UserId(managerSpace, managerEmail).toString());
		return this.itemConverter.toBoundary(this.itemDao.save(itemEntity));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAll(String userSpace, String userEmail) {
		return StreamSupport
				.stream(this.itemDao.findAll().spliterator(), false) // Iterable to Stream<ItemEntity>,
				.map(entity -> this.itemConverter.toBoundary(entity)) // Stream<ItemBoundary>
				.collect(Collectors.toList()); // List<ItemBoundary>
	}

	@Override
	@Transactional(readOnly = true)
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		Optional<ItemEntity> existingItem = this.itemDao.findById(new ItemId(itemSpace, itemId).toString());
		if (!existingItem.isPresent())
			throw new ItemNotFoundException("item with id: " + itemId + "and space: " + itemSpace + " does not exist");
		return this.itemConverter.toBoundary(existingItem.get());
	}

	@Override
	@Transactional
	public void deleteAll(String adminSpace, String adminEmail) {
		Optional<UserEntity> existingAdmin = this.userDao.findById(new UserId(adminSpace, adminEmail).toString());
		if(!existingAdmin.isPresent())
		{
			throw new AdminNotFoundException("Admin with email: " + existingAdmin + "does not exist");
		}
		UserEntity existingAdminEntity = existingAdmin.get();
		if(!existingAdminEntity.getRole().equals(UserRole.ADMIN))
		{
			throw new InvalidUserException("Invalid role: " + existingAdminEntity.getRole()
			+ " for user: " + existingAdminEntity.getUsername());
		}
		this.itemDao.deleteAll();
	}

	@Override
	public void bindItemToChildItem(String managerSpace, String managerEmail, String itemSpace, String itemId) {
		// TODO
	}

	@Override
	public ItemBoundary[] getAllItemChildren(String userSpace, String userEmail, String itemSpace, String itemId) {
		// TODO
		return null;
	}

	@Override
	public ItemBoundary[] getItemParents(String userSpace, String userEmail, String itemSpace, String itemId) {
		// TODO
		return null;
	}

}