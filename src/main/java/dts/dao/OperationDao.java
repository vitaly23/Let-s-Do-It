package dts.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import dts.data.OperationEntity;

public interface OperationDao extends PagingAndSortingRepository<OperationEntity, String> {

}
