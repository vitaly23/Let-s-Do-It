package dts.dao;

import org.springframework.data.repository.CrudRepository;

import dts.data.ItemEntity;

public interface ItemDao extends CrudRepository<ItemEntity, String> {

}
