package dts.dao;

import org.springframework.data.repository.CrudRepository;

import dts.data.IdGeneratorEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IdGeneratorDao extends CrudRepository<IdGeneratorEntity, String> {

}
