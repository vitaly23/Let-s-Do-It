package dts.logic;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dts.converter.OperationConverter;
import dts.dao.IdGeneratorDao;
import dts.dao.OperationDao;
import dts.data.IdGeneratorEntity;
import dts.data.OperationEntity;
import models.operations.OperationId;
import boundaries.OperationBoundary;

@Service
public class EnhancedOperationsServiceImplementation implements OperationsService {

	private String spaceName;
	private OperationDao operationDao;
	private OperationConverter operationConverter;
	private IdGeneratorDao idGeneratorDao;

	@Value("${spring.application.name:default_space_name}")
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	@Autowired
	public EnhancedOperationsServiceImplementation(OperationDao operationDao, OperationConverter operationConverter,
			IdGeneratorDao idGeneratorDao) {
		this.operationDao = operationDao;
		this.operationConverter = operationConverter;
		this.idGeneratorDao = idGeneratorDao;
	}

	@Override
	@Transactional
	public Object invokeOpreation(OperationBoundary operation) {
		OperationEntity operationEntity = this.operationConverter.toEntity(operation);
		IdGeneratorEntity idGeneratorEntity = new IdGeneratorEntity();
		idGeneratorEntity = this.idGeneratorDao.save(idGeneratorEntity);
		Long newId = idGeneratorEntity.getId();
		this.idGeneratorDao.deleteById(newId);
		String strId = "" + newId;
		operationEntity.setOperationId(new OperationId(this.spaceName, strId).toString());
		operationEntity.setCreatedTimestamp(new Date());
		operationEntity = this.operationDao.save(operationEntity);
		return this.operationConverter.toBoundary(operationEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		return StreamSupport
				.stream(this.operationDao.findAll().spliterator(), false) // Iterable to Stream<OperationEntity>,
				.map(entity -> this.operationConverter.toBoundary(entity)) // Stream<OperationBoundary>
				.collect(Collectors.toList()); // List<OperationBoundary>
	}

	@Override
	@Transactional
	public void deleteAllActions(String adminSpace, String adminEmail) {
		this.operationDao.deleteAll();
	}
	
}
