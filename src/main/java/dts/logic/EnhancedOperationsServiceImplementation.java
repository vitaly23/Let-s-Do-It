package dts.logic;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dts.converter.OperationConverter;
import dts.dao.IdGeneratorDao;
import dts.dao.ItemDao;
import dts.dao.OperationDao;
import dts.dao.UserDao;
import dts.data.IdGeneratorEntity;
import dts.data.ItemEntity;
import dts.data.OperationEntity;
import dts.data.UserEntity;
import dts.data.UserRole;
import dts.utils.ValidationService;
import exceptions.InvalidOperationTypeException;
import exceptions.InvalidUserException;
import exceptions.ItemNotFoundException;
import exceptions.UserNotFoundException;
import models.operations.ItemId;
import models.operations.OperationId;
import models.users.UserId;
import boundaries.OperationBoundary;

@Service
public class EnhancedOperationsServiceImplementation implements OperationsService {

	private String spaceName;
	private OperationDao operationDao;
	private OperationConverter operationConverter;
	private IdGeneratorDao idGeneratorDao;
	private UserDao userDao;
	private ItemDao itemDao;
	private ValidationService validationService; 

	@Value("${spring.application.name:default_space_name}")
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	@Autowired
	public EnhancedOperationsServiceImplementation(OperationDao operationDao, 
			OperationConverter operationConverter,
			IdGeneratorDao idGeneratorDao,
			UserDao userDao,
			ItemDao itemDao,
			ValidationService validationService) {
		this.operationDao = operationDao;
		this.operationConverter = operationConverter;
		this.idGeneratorDao = idGeneratorDao;
		this.userDao = userDao;
		this.itemDao = itemDao;
		this.validationService = validationService;
	}

	@Override
	@Transactional
	public Object invokeOpreation(OperationBoundary operation) {
		OperationEntity operationEntity = this.operationConverter.toEntity(operation);
		if(operationEntity.getType().isEmpty() ||
		   operationEntity.getType() == null)
		{
			throw new InvalidOperationTypeException("Invalid type: " + 
					operationEntity.getType() + " for operation: " + 
					operationEntity.getOperationId());
		}
		Optional<UserEntity> existingUser = this.userDao.findById(operationEntity.getInvokedBy());
		this.validationService.ValidateUserFound(existingUser, operationEntity.getInvokedBy());

		Optional<ItemEntity> existingItem = this.itemDao.findById(operationEntity.getItem());
		if (!existingItem.isPresent())
		{			
			throw new ItemNotFoundException("Item with id: " + operationEntity.getItem() + 
					" does not exist");
		}

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
		Optional<UserEntity> existingAdmin = this.userDao.findById(new UserId(adminSpace, adminEmail).toString());
		this.validationService.ValidateUserFound(existingAdmin, adminEmail);

		UserEntity existingAdminEntity = existingAdmin.get();
		this.validationService.ValidateRole(existingAdmin, UserRole.ADMIN);	

		return StreamSupport
				.stream(this.operationDao.findAll().spliterator(), false) // Iterable to Stream<OperationEntity>,
				.map(entity -> this.operationConverter.toBoundary(entity)) // Stream<OperationBoundary>
				.collect(Collectors.toList()); // List<OperationBoundary>
	}

	@Override
	@Transactional
	public void deleteAllActions(String adminSpace, String adminEmail) {
		Optional<UserEntity> existingAdmin = this.userDao.findById(new UserId(adminSpace, adminEmail).toString());
		this.validationService.ValidateUserFound(existingAdmin, adminEmail);

		UserEntity existingAdminEntity = existingAdmin.get();
		this.validationService.ValidateRole(existingAdmin, UserRole.ADMIN);	
		this.operationDao.deleteAll();
	}
	
}
