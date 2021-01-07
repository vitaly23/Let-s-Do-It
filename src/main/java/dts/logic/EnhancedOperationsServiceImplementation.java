package dts.logic;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dts.converter.OperationConverter;

import dts.dao.IdGeneratorDao;
import dts.dao.OperationDao;

import dts.data.IdGeneratorEntity;
import dts.data.OperationEntity;
import dts.data.UserEntity;
import dts.data.UserRole;

import dts.operations.Operations;

import dts.utils.OperationHelper;
import dts.utils.UserHelper;

import exceptions.InvalidOperationException;

import models.operations.OperationId;

import boundaries.OperationBoundary;

@Service
public class EnhancedOperationsServiceImplementation implements OperationsService {

	private String spaceName;
	private OperationDao operationDao;
	private OperationConverter operationConverter;
	private IdGeneratorDao idGeneratorDao;
	private OperationHelper operationHelper;
	private ConfigurableApplicationContext appContext;
	private UserHelper userHelper;

	@Value("${spring.application.name:default_space_name}")
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	@Autowired
	public EnhancedOperationsServiceImplementation(OperationDao operationDao, OperationConverter operationConverter,
			IdGeneratorDao idGeneratorDao, OperationHelper operationHelper, ConfigurableApplicationContext appContext,
			UserHelper userHelper) {
		this.operationDao = operationDao;
		this.operationConverter = operationConverter;
		this.idGeneratorDao = idGeneratorDao;
		this.operationHelper = operationHelper;
		this.appContext = appContext;
		this.userHelper = userHelper;
	}

	@Override
	@Transactional
	public Object invokeOpreation(OperationBoundary operation) {
		OperationEntity operationEntity = this.operationConverter.toEntity(operation);
		this.operationHelper.ValidateOpertaionData(operationEntity);
		IdGeneratorEntity idGeneratorEntity = new IdGeneratorEntity();
		idGeneratorEntity = this.idGeneratorDao.save(idGeneratorEntity);
		//idGeneratorEntity for h2
		//Long newId = idGeneratorEntity.getId();
		//this.idGeneratorDao.deleteById(newId);
		//String strId = "" + newId;
		String strId = idGeneratorEntity.getId();
		operationEntity.setOperationId(new OperationId(this.spaceName, strId).toString());
		operationEntity.setCreatedTimestamp(new Date());
		this.operationDao.save(operationEntity);
		try {
			Operations operations = this.appContext.getBean(operation.getType(), Operations.class);
			return operations.invokeOperation(operation);
		} catch (BeansException e) {
			throw new InvalidOperationException("Operation of type " + operation.getType() + " does not exist");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail, int size, int page) {
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(adminSpace, adminEmail, UserRole.ADMIN);
		return StreamSupport
				.stream(this.operationDao
						.findAll(PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "operationId"))
						.spliterator(), false)
				.map(entity -> this.operationConverter.toBoundary(entity)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteAllActions(String adminSpace, String adminEmail) {
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(adminSpace, adminEmail, UserRole.ADMIN);
		this.operationDao.deleteAll();
	}

}
