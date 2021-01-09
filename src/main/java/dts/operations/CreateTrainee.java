package dts.operations;

import java.util.List;
import java.util.Map;

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
import dts.data.UserEntity;
import dts.data.UserRole;
import dts.logic.EnhancedItemsService;
import dts.utils.OperationHelper;
import dts.utils.UserHelper;

import exceptions.TraineeAlreadyExistsException;

import models.users.UserId;

@Component(OperationTypes.CREATE_TRAINEE)
public class CreateTrainee implements Operations {

	private Log log = LogFactory.getLog(Application.class);
	private EnhancedItemsService itemsService;
	private OperationHelper operationHelper;
	private UserHelper userHelper;

	@Autowired
	public CreateTrainee(EnhancedItemsService itemsService, OperationHelper operationHelper, UserHelper userHelper) {
		this.itemsService = itemsService;
		this.operationHelper = operationHelper;
		this.userHelper = userHelper;
	}

	@Override
	@Transactional
	public Object invokeOperation(OperationBoundary operationBoundary) {
		this.operationHelper.validateOperationAttributes(operationBoundary.getOperationAttributes(),
				TraineeAttributes.ALL_TRAINEE_ATTRIBUTES);
		UserId userId = operationBoundary.getInvokedBy().getUserId();
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(userId.getSpace(), userId.getEmail(),
				UserRole.PLAYER);
		this.validateNoSuchTrainee(userId.toString());
		this.userHelper.changeUserRole(existingUser, UserRole.MANAGER);
		Map<String, Object> operationAttributes = operationBoundary.getOperationAttributes();
		Object[] args = this.operationHelper.getNameAndLatAndLng(operationAttributes);
		ItemBoundary traineeBoundary = new ItemBoundary(ItemTypes.TRAINEE, (String) args[0], (double) args[1],
				(double) args[2], operationAttributes);
		this.log.debug("Creating new digital item of type " + ItemTypes.TRAINEE);
		ItemBoundary trainee = this.itemsService.create(userId.getSpace(), userId.getEmail(), traineeBoundary);
		this.userHelper.changeUserRole(existingUser, UserRole.PLAYER);
		return trainee;
	}

	@Transactional(readOnly = true)
	public void validateNoSuchTrainee(String userId) {
		List<ItemBoundary> traineeList = this.itemsService.getAllActiveByTypeAndCreatedBy(userId, ItemTypes.TRAINEE, 1,
				0);
		if (!traineeList.isEmpty()) {
			throw new TraineeAlreadyExistsException("Trainee with userId: " + userId + " already exists");
		}
	}
}
