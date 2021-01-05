package dts.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import dts.data.UserEntity;

public interface UserDao extends PagingAndSortingRepository<UserEntity, String> {

}
