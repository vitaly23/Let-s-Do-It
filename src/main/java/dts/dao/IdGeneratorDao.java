package dts.dao;

import org.springframework.data.repository.CrudRepository;

import dts.data.IdGeneratorEntity;

public interface IdGeneratorDao extends CrudRepository<IdGeneratorEntity, Long> {

}
