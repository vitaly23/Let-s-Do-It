package dts.dao;

import org.springframework.data.repository.CrudRepository;

import dts.data.ItemEntity;
import models.operations.ItemIdentifier;

public interface ItemRepository extends CrudRepository<ItemEntity, ItemIdentifier> {

}
