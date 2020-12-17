package dts.dao;

import org.springframework.data.repository.CrudRepository;

import dts.data.OperationEntity;

public interface OperationDao extends CrudRepository<OperationEntity, String> {

}
