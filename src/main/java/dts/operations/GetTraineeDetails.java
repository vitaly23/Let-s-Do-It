package dts.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boundaries.ItemBoundary;
import boundaries.OperationBoundary;

import constants.ItemTypes;
import constants.OperationTypes;

import dts.logic.EnhancedItemsService;

import exceptions.TraineeNotFoundException;

import models.users.UserId;

@Component(OperationTypes.GET_TRAINEE_DETAILS)
public class GetTraineeDetails implements Operations {

	private EnhancedItemsService itemsService;

	@Autowired
	public GetTraineeDetails(EnhancedItemsService itemsService) {
		this.itemsService = itemsService;
	}

	@Override
	@Transactional(readOnly = true)
	public Object invokeOperation(OperationBoundary operationBoundary) {
		UserId userId = operationBoundary.getInvokedBy().getUserId();
		List<ItemBoundary> traineeList = this.itemsService.getAllByTypeAndCreatedBy(userId.toString(), ItemTypes.TRAINEE, 1, 0);
		if (traineeList.isEmpty())
			throw new TraineeNotFoundException("Trainee that was created by: " + userId.toString() + " does not exist");
		return traineeList.get(0);
	}

}
