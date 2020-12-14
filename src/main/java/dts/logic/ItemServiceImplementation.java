package dts.logic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import boundaries.ItemBoundary;
import dts.converter.ItemConverter;
import dts.dao.ItemIdRepository;
import dts.dao.ItemRepository;
import dts.data.ItemEntity;
import dts.data.ItemIdEntity;
import models.operations.ItemIdentifier;
import models.users.UserId;

@Service
public class ItemServiceImplementation implements ExtendedItemService {

	private String spaceName;
	private Map<ItemIdentifier, ItemEntity> itemStore;
	private ItemRepository itemRepository;
	private ItemConverter itemConvertor;
	private ItemIdRepository itemIdRepository;

	@Value("${spring.application.name:default}")
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	@Autowired
	public ItemServiceImplementation(ItemConverter itemConvertor, ItemRepository itemRepository,
			ItemIdRepository itemIdRepository) {
		this.itemConvertor = itemConvertor;
		this.itemRepository = itemRepository;
		this.itemIdRepository = itemIdRepository;
	}

	@Override
	public ItemBoundary create(String managerSpace, String managerEmail, ItemBoundary newItem) {
		// generate new id for item
		ItemIdEntity itemIdEntity = new ItemIdEntity();
		itemIdEntity = this.itemIdRepository.save(itemIdEntity);
		Long numricId = itemIdEntity.getId();
		this.itemIdRepository.deleteById(numricId);
		// create new item and save to the db
		String strId = "" + numricId;
		newItem.setCreatedTimestamp(new Date());
		newItem.setItemId(new ItemIdentifier(spaceName, strId));
		newItem.setCreatedBy(new UserId(managerSpace, managerEmail));
		ItemEntity item = this.itemConvertor.toEntity(newItem);
		itemRepository.save(item);
		return this.itemConvertor.toBoundary(item);
	}

	@Override
	public ItemBoundary update(String managerSpace, String managerEmail, String itemSpace, String itemId,
			ItemBoundary update) {
		ItemIdentifier itemIdentifier = new ItemIdentifier(itemSpace, itemId);
		Optional<ItemEntity> itemOptional = this.itemRepository.findById(itemIdentifier);
		if (!itemOptional.isPresent()) {
			throw new RuntimeException("The item don't exist");
		}
		ItemEntity item = itemOptional.get(); 
		// this details can't be change
		Date date = item.getCreatedTimestamp();
		ItemIdentifier itemIdConst = item.getItemId();

		item = this.itemConvertor.toEntity(update);
		item.setCreatedTimestamp(date);
		item.setItemId(itemIdConst);
		itemStore.put(item.getItemId(), item);

		return this.itemConvertor.toBoundary(item);
	}

	@Override
	public List<ItemBoundary> getAll(String userSpace, String userEmail) {
		return StreamSupport.stream(
				this.itemRepository.findAll().spliterator(),  
				false) // Iterable to Stream<ItemEntity>,
			.map(entity->this.itemConvertor.toBoundary(entity)) // Stream<ItemBoundary>
			.collect(Collectors.toList()); // List<ItemBoundary>
	}

	@Override
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		ItemIdentifier itemIdentifier = new ItemIdentifier(itemSpace, itemId);
		Optional<ItemEntity> itemOptinal = this.itemRepository.findById(itemIdentifier);
		if (!itemOptinal.isPresent()) {
			throw new RuntimeException("The item don't exist");
		}
		return this.itemConvertor.toBoundary(itemOptinal.get());
	}

	@Override
	public void deleteAll(String adminSpace, String adminEmail) {
		this.itemRepository.deleteAll();
	}

	public ItemConverter getItemConvertor() {
		return itemConvertor;
	}

	public void setItemConvertor(ItemConverter itemConvertor) {
		this.itemConvertor = itemConvertor;
	}

	@Override
	public void bindItemToChildItem(String managerSpace, String managerEmail, String itemSpace, String itemId) {
		
	}

	@Override
	public ItemBoundary[] getAllItemChildren(String userSpace, String userEmail, String itemSpace, String itemId) {
		
		return null;
	}

	@Override
	public ItemBoundary[] getItemParents(String userSpace, String userEmail, String itemSpace, String itemId) {
		
		return null;
	}

}