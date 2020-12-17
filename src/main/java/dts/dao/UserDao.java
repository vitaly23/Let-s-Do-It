package dts.dao;

import org.springframework.data.repository.CrudRepository;

import dts.data.UserEntity;

public interface UserDao extends CrudRepository<UserEntity, String> {

}
