package dts.logic;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import boundaries.DigitalItemBoundary;
import dts.data.ItemEntity;
import logic.ItemConverter;
import models.operations.ItemIdentifier;
import models.users.User;

@Service
public class ItemServiceImplementation implements ItemService {

	private String spaceName;
	private Map<ItemIdentifier, ItemEntity> itemStore;
	private AtomicLong idGenerator;
	private ItemConverter itemConvertor;

	@Value("${spring.application.name:2021a.integrative.sun1700.letsdoit}")
	public void setHelperName(String spaceName) {
		this.spaceName = spaceName;
	}

	@Autowired
	public void setMessageConverter(ItemConverter itemConvertor) {
		this.itemConvertor = itemConvertor;
	}

	@PostConstruct
	public void init() {
		this.itemStore = Collections.synchronizedMap(new HashMap<>());
		this.idGenerator = new AtomicLong(1l);
	}

	@Override
	public DigitalItemBoundary create(String managerSpace, String managerEmail, DigitalItemBoundary newItem) {
		String id = "" + idGenerator.getAndIncrement();
		newItem.setCreatedTimestamp(new Date());
		newItem.setItemId(new ItemIdentifier(spaceName, id));
		newItem.setCreatedBy(new User(managerSpace, managerEmail));
		ItemEntity entity = this.itemConvertor.toEntity(newItem);
		itemStore.put(entity.getItemId(), entity);

		return newItem;
	}

	@Override
	public DigitalItemBoundary update(String managerSpace, String managerEmail, String itemSpace, String itemId,

			DigitalItemBoundary update) {
		ItemIdentifier itemIdentifier= new ItemIdentifier(itemSpace,itemId);
		ItemEntity item = this.itemStore.get(itemIdentifier);
		if (item == null) {
			throw new RuntimeException("The item don't exist");
		}
		//this details can't be change
		Date date=item.getCreatedTimestamp();
		ItemIdentifier itemIdConst=item.getItemId();
		
		item = this.itemConvertor.toEntity(update);
		item.setCreatedTimestamp(date);
		item.setItemId(itemIdConst);
		itemStore.put(item.getItemId(), item);

		return this.itemConvertor.toBoundary(item);
	}

	@Override
	public List<DigitalItemBoundary> getAll(String userSpace, String userEmail) {
		return this.itemStore.values().stream().map(entity -> itemConvertor.toBoundary(entity)).collect(Collectors.toList());
	}

	@Override
	public DigitalItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		ItemIdentifier itemIdentifier= new ItemIdentifier(itemSpace,itemId);
		ItemEntity item = this.itemStore.get(itemIdentifier);
		if (item == null) {
			throw new RuntimeException("The item don't exist");
		}
		return this.itemConvertor.toBoundary(item);
	}

	@Override
	public void deleteAll(String adminSpace, String adminEmail) {
		this.itemStore.clear();
	}

}