package dts.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dts.data.UserEntity;

@Repository
public interface UserDao extends PagingAndSortingRepository<UserEntity, String> {

}
