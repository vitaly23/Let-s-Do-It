package dts.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dts.data.IdGeneratorEntity;

@Repository
public interface IdGeneratorDao extends CrudRepository<IdGeneratorEntity, String> {

}
