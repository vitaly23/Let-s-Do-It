package dts.logic;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boundaries.ItemBoundary;

import dts.converter.ItemConverter;
import dts.dao.IdGeneratorDao;
import dts.dao.ItemDao;
import dts.data.IdGeneratorEntity;
import dts.data.ItemEntity;
import dts.data.UserEntity;
import dts.data.UserRole;
import dts.utils.ItemHelper;
import dts.utils.UserHelper;

import exceptions.RoleViolationException;

import models.operations.ItemId;

@Service
public class EnhancedItemsServiceImplementation implements EnhancedItemsService {

	private ItemDao itemDao;
	private ItemConverter itemConverter;
	private IdGeneratorDao idGeneratorDao;
	private UserHelper userHelper;
	private ItemHelper itemHelper;

	@Autowired
	public EnhancedItemsServiceImplementation(ItemDao itemDao, ItemConverter itemConvertor,
			IdGeneratorDao idGeneratorDao, UserHelper userHelper, ItemHelper itemHelper) {
		this.itemDao = itemDao;
		this.itemConverter = itemConvertor;
		this.idGeneratorDao = idGeneratorDao;
		this.userHelper = userHelper;
		this.itemHelper = itemHelper;
	}

	@Override
	@Transactional
	public ItemBoundary create(String managerSpace, String managerEmail, ItemBoundary newItem) {
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(managerSpace, managerEmail, UserRole.MANAGER);
		ItemEntity newItemEntity = this.itemConverter.toEntity(newItem);
		this.itemHelper.ValidateItemData(newItemEntity);
		IdGeneratorEntity idGeneratorEntity = new IdGeneratorEntity();
		idGeneratorEntity = this.idGeneratorDao.save(idGeneratorEntity);
		Long numricId = idGeneratorEntity.getId();
		this.idGeneratorDao.deleteById(numricId);
		String strId = "" + numricId;
		newItemEntity.setCreatedTimestamp(new Date());
		newItemEntity.setItemId(new ItemId(managerSpace, strId).toString());
		newItemEntity.setCreatedBy(existingUser.getUserId());
		this.itemDao.save(newItemEntity);
		return this.itemConverter.toBoundary(newItemEntity);
	}

	@Override
	@Transactional
	public ItemBoundary update(String managerSpace, String managerEmail, String itemSpace, String itemId,
			ItemBoundary update) {
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(managerSpace, managerEmail, UserRole.MANAGER);
		ItemEntity existingItem = this.itemHelper.getSpecificItem(itemSpace, itemId);
		ItemEntity updatedItem = this.itemConverter.toEntity(update);
		if (update.getType() != null)
			existingItem.setType(updatedItem.getType());
		if (update.getName() != null)
			existingItem.setName(updatedItem.getName());
		if (update.getActive() != null)
			existingItem.setActive(updatedItem.getActive());
		if (update.getLocation() != null) {
			existingItem.setLat(updatedItem.getLat());
			existingItem.setLng(updatedItem.getLng());
		}
		if (update.getItemAttributes() != null)
			existingItem.setItemAttributes(updatedItem.getItemAttributes());
		this.itemHelper.ValidateItemData(existingItem);
		return this.itemConverter.toBoundary(this.itemDao.save(existingItem));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAll(String userSpace, String userEmail, int size, int page) {
		UserEntity existingUser = this.userHelper.getSpecificUser(userSpace, userEmail);
		Pageable pageRequest = PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId");
		if (existingUser.getRole() == UserRole.MANAGER)
			return StreamSupport.stream(this.itemDao.findAll(pageRequest).spliterator(), false)
					.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		else if (existingUser.getRole() == UserRole.PLAYER)
			return StreamSupport.stream(this.itemDao.findAllByActiveTrue(pageRequest).spliterator(), false)
					.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		else
			throw new RoleViolationException(
					"User: " + existingUser.getUserId() + " has insufficient privileges to get all Items");
	}

	@Override
	@Transactional(readOnly = true)
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		UserEntity existingUser = this.userHelper.getSpecificUser(userSpace, userEmail);
		if (existingUser.getRole() == UserRole.MANAGER)
			return this.itemConverter.toBoundary(this.itemHelper.getSpecificItem(itemSpace, itemId));
		else if (existingUser.getRole() == UserRole.PLAYER)
			return this.itemConverter.toBoundary(this.itemHelper.getActiveSpecificItem(itemSpace, itemId));
		else
			throw new RoleViolationException(
					"User: " + existingUser.getUserId() + " has insufficient privileges to retrive a specific Item");
	}

	@Override
	@Transactional
	public void deleteAll(String adminSpace, String adminEmail) {
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(adminSpace, adminEmail, UserRole.ADMIN);
		this.itemDao.deleteAll();
	}

	@Override
	@Transactional
	public void bindChild(String managerSpace, String managerEmail, String itemSpace, String itemId, ItemId item) {
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(managerSpace, managerEmail, UserRole.MANAGER);
		ItemEntity parentItem = this.itemHelper.getSpecificItem(itemSpace, itemId);
		ItemEntity childItem = this.itemHelper.getSpecificItem(item.getSpace(), item.getId());
		childItem.addParent(parentItem);
		this.itemDao.save(childItem);
		parentItem.addChild(childItem);
		this.itemDao.save(parentItem);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllChildren(String userSpace, String userEmail, String itemSpace, String itemId,
			int size, int page) {
		UserEntity existingUser = this.userHelper.getSpecificUser(userSpace, userEmail);
		Pageable pageRequest = PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId");
		if (existingUser.getRole() == UserRole.MANAGER)
			return StreamSupport
					.stream(this.itemDao
							.findAllByItemParents_itemId(new ItemId(itemSpace, itemId).toString(), pageRequest)
							.spliterator(), false) // Iterable to Stream<ItemEntity>,
					.map(entity -> this.itemConverter.toBoundary(entity)) // Stream<ItemBoundary>
					.collect(Collectors.toList()); // List<ItemBoundary>
		else if (existingUser.getRole() == UserRole.PLAYER)
			return StreamSupport
					.stream(this.itemDao.findAllByActiveTrueAndItemParents_itemId(
							new ItemId(itemSpace, itemId).toString(), pageRequest).spliterator(), false)
					.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		else
			throw new RoleViolationException(
					"User: " + existingUser.getUserId() + " has insufficient privileges to get all Item's children");
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getParents(String userSpace, String userEmail, String itemSpace, String itemId, int size,
			int page) {
		UserEntity existingUser = this.userHelper.getSpecificUser(userSpace, userEmail);
		Pageable pageRequest = PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId");
		if (existingUser.getRole() == UserRole.MANAGER)
			return StreamSupport
					.stream(this.itemDao
							.findAllByItemChildren_itemId(new ItemId(itemSpace, itemId).toString(), pageRequest)
							.spliterator(), false) // Iterable to Stream<ItemEntity>,
					.map(entity -> this.itemConverter.toBoundary(entity)) // Stream<ItemBoundary>
					.collect(Collectors.toList()); // List<ItemBoundary>
		else if (existingUser.getRole() == UserRole.PLAYER)
			return StreamSupport
					.stream(this.itemDao.findAllByActiveTrueAndItemChildren_itemId(
							new ItemId(itemSpace, itemId).toString(), pageRequest).spliterator(), false)
					.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		else
			throw new RoleViolationException(
					"User: " + existingUser.getUserId() + " has insufficient privileges to get all Item's parents");
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllByNamePattern(String userSpace, String userEmail, String namePattern, int size,
			int page) {
		UserEntity existingUser = this.userHelper.getSpecificUser(userSpace, userEmail);
		Pageable pageRequest = PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId");
		if (existingUser.getRole() == UserRole.MANAGER)
			return StreamSupport
					.stream(this.itemDao.findAllByNameLikeIgnoreCase(namePattern, pageRequest).spliterator(), false) // Iterable
																														// to
																														// Stream<ItemEntity>,
					.map(entity -> this.itemConverter.toBoundary(entity)) // Stream<ItemBoundary>
					.collect(Collectors.toList()); // List<ItemBoundary>
		else if (existingUser.getRole() == UserRole.PLAYER)
			return StreamSupport
					.stream(this.itemDao.findAllByActiveTrueAndNameLikeIgnoreCase(namePattern, pageRequest)
							.spliterator(), false)
					.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		else
			throw new RoleViolationException("User: " + existingUser.getUserId()
					+ " has insufficient privileges to get all Items by name pattern");
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllByType(String userSpace, String userEmail, String type, int size, int page) {
		UserEntity existingUser = this.userHelper.getSpecificUser(userSpace, userEmail);
		Pageable pageRequest = PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId");
		if (existingUser.getRole() == UserRole.MANAGER)
			return StreamSupport.stream(this.itemDao.findAllByType(type, pageRequest).spliterator(), false)
					.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		else if (existingUser.getRole() == UserRole.PLAYER)
			return StreamSupport.stream(this.itemDao.findAllByActiveTrueAndType(type, pageRequest).spliterator(), false)
					.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		else
			throw new RoleViolationException(
					"User: " + existingUser.getUserId() + " has insufficient privileges to get all Items by type");
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllByLocation(String userSpace, String userEmail, double lat, double lng,
			double distance, int size, int page) {
		UserEntity existingUser = this.userHelper.getSpecificUser(userSpace, userEmail);
		Pageable pageRequest = PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId");
		if (existingUser.getRole() == UserRole.MANAGER)
			return StreamSupport
					.stream(this.itemDao.findAllByLatBetweenAndLngBetween(lat - distance, lat + distance,
							lng - distance, lng + distance, pageRequest).spliterator(), false)
					.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		else if (existingUser.getRole() == UserRole.PLAYER)
			return StreamSupport
					.stream(this.itemDao.findAllByActiveTrueAndLatBetweenAndLngBetween(lat - distance, lat + distance,
							lng - distance, lng + distance, pageRequest).spliterator(), false)
					.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		else
			throw new RoleViolationException(
					"User: " + existingUser.getUserId() + " has insufficient privileges to get all Items by location");
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllByTypeAndCreatedBy(String userId, String type, int size, int page) {
		Pageable pageRequest = PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId");
		return StreamSupport
				.stream(this.itemDao.findAllByActiveTrueAndTypeAndCreatedBy(type, userId, pageRequest).spliterator(),
						false)
				.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllJoinedMeetings(String userId, String itemId, int size, int page) {
		Pageable pageRequest = PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId");
		return StreamSupport
				.stream(this.itemDao
						.findAllByActiveTrueAndCreatedByNotAndItemChildren_itemId(userId, itemId, pageRequest)
						.spliterator(), false)
				.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
	}

}