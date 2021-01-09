package dts.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dts.data.OperationEntity;

@Repository
public interface OperationDao extends PagingAndSortingRepository<OperationEntity, String> {

}
