package main.dts.logic;

import main.dts.data.*;
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


import main.dts.convertor.ItemConverter;

import main.dts.data.ItemEntity;

import main.boundaries.DigitalItemBoundary;
import main.models.operation.ItemIdentifier;

import main.models.operation.UserId;


public class ItemServiceImplementation implements ItemService {


	private String spaceName;

	private Map<String, ItemEntity> itemStore;

	private AtomicLong idGenerator;

	private ItemConverter itemConvertor;


	@Value("${spring.application.name:demodemo}")

	public void setHelperName(String spaceName) {

		this.spaceName = spaceName;

	}


	@Autowired

	public void setMessageConverter(ItemConverter itemConvertor) {

		this.itemConvertor = itemConvertor;

	}


	@PostConstruct

	public void init() {

		// thread safe map

		this.itemStore = Collections.synchronizedMap(new HashMap<>());

		this.idGenerator = new AtomicLong(1l);

	}


	// To do create with String managerSpace, String managerEmail

	@Override

	public DigitalItemBoundary create(String managerSpace, String managerEmail, DigitalItemBoundary newItem) {

		String id = "" + idGenerator.getAndIncrement();


		newItem.setCreatedTimestamp(new Date());

		

		newItem.setItemId(new ItemIdentifier(spaceName,id));

		

		newItem.setCreatedBy(new UserId(managerSpace, managerEmail));


		ItemEntity entity = this.itemConvertor.toEntity(newItem);


		itemStore.put(id, entity);


		return newItem;

	}


	@Override

	public DigitalItemBoundary update(String managerSpace, String managerEmail, String itemSpace, String itemId,

			DigitalItemBoundary update) {

		ItemEntity item = this.itemStore.get(itemId);

		if (item == null) {

			throw new RuntimeException("The item don't exist");

		}

		

		// update ??

		item = this.itemConvertor.toEntity(update);

		

		itemStore.put(itemId, item);


		return this.itemConvertor.toBoundary(item);

	}


	@Override

	public List<DigitalItemBoundary> getAll(String userSpace, String userEmail) {


		return this.itemStore.values().stream().map(entity -> itemConvertor.toBoundary(entity))

				.collect(Collectors.toList());

	}


	@Override

	public DigitalItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {

		ItemEntity item = this.itemStore.get(itemId);

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