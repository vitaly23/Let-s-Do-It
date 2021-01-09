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
import dts.utils.OperationHelper;

import models.users.UserId;

@Component(OperationTypes.GET_TRAINEE_DETAILS)
public class GetTraineeDetails implements Operations {

	private EnhancedItemsService itemsService;
	private OperationHelper operationHelper;

	@Autowired
	public GetTraineeDetails(EnhancedItemsService itemsService, OperationHelper operationHelper) {
		this.itemsService = itemsService;
		this.operationHelper = operationHelper;
	}

	@Override
	@Transactional(readOnly = true)
	public Object invokeOperation(OperationBoundary operationBoundary) {
		UserId userId = operationBoundary.getInvokedBy().getUserId();
		List<ItemBoundary> traineeList = this.itemsService.getAllActiveByTypeAndCreatedBy(userId.toString(),
				ItemTypes.TRAINEE, 1, 0);
		ItemBoundary traineeBoundary = (ItemBoundary) this.operationHelper.getFoundTrainee(userId.toString(),
				traineeList);
		return traineeBoundary;
	}

}
