package dts.operations;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boundaries.ItemBoundary;
import boundaries.OperationBoundary;

import constants.ItemTypes;
import constants.OperationTypes;
import constants.TraineeAttributes;

import dts.Application;
import dts.dao.ItemDao;
import dts.data.ItemEntity;
import dts.data.UserEntity;
import dts.data.UserRole;
import dts.logic.ItemsService;
import dts.utils.OperationHelper;
import dts.utils.UserHelper;

import exceptions.TraineeAlreadyExistsException;

import models.users.UserId;

@Component(OperationTypes.CREATE_TRAINEE)
public class CreateTrainee implements Operations {

	private Log log = LogFactory.getLog(Application.class);
	private ItemsService itemsService;
	private ItemDao itemDao;
	private OperationHelper operationHelper;
	private UserHelper userHelper;

	@Autowired
	public CreateTrainee(ItemsService itemsService, ItemDao itemDao, OperationHelper operationHelper,
			UserHelper userHelper) {
		this.itemsService = itemsService;
		this.itemDao = itemDao;
		this.operationHelper = operationHelper;
		this.userHelper = userHelper;
	}

	@Override
	@Transactional
	public Object invokeOperation(OperationBoundary operationBoundary) {
		this.operationHelper.ValidateOperationAttributes(operationBoundary.getOperationAttributes(),
				TraineeAttributes.TRAINEE_ATTRIBUTES);
		UserId userId = operationBoundary.getInvokedBy().getUserId();
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(userId.getSpace(), userId.getEmail(),
				UserRole.PLAYER);
		this.ValidateNoSuchTrainee(userId.toString());
		this.log.debug("Setting user with email '" + userId.getEmail() + "' to Manager role");
		this.userHelper.ChangeUserRole(existingUser, UserRole.MANAGER);
		Map<String, Object> operationAttributes = operationBoundary.getOperationAttributes();
		String itemName = (String) operationAttributes.remove(TraineeAttributes.NAME);
		ItemBoundary traineeBoundary = new ItemBoundary(ItemTypes.TRAINEE, itemName, operationAttributes);
		this.log.debug("Creating new digital item of type " + ItemTypes.TRAINEE);
		ItemBoundary trainee = this.itemsService.create(userId.getSpace(), userId.getEmail(), traineeBoundary);
		this.log.debug("Setting user with email '" + userId.getEmail() + "' to Player role");
		this.userHelper.ChangeUserRole(existingUser, UserRole.PLAYER);
		return trainee;
	}

	@Transactional(readOnly = true)
	public void ValidateNoSuchTrainee(String userId) {
		Optional<ItemEntity> optionalTrainee = this.itemDao.findByActiveTrueAndTypeAndCreatedBy(ItemTypes.TRAINEE,
				userId);
		if (optionalTrainee.isPresent()) {
			throw new TraineeAlreadyExistsException("Trainee with userId: " + userId + " already exists");
		}
	}
}
