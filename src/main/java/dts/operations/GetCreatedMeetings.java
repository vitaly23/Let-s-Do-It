package dts.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boundaries.OperationBoundary;

import constants.ItemTypes;
import constants.OperationTypes;

import dts.logic.EnhancedItemsService;
import dts.utils.OperationHelper;

import models.users.UserId;

@Component(OperationTypes.GET_CREATED_MEETINGS)
public class GetCreatedMeetings implements Operations {

	private OperationHelper operationHelper;
	private EnhancedItemsService itemsService;

	@Autowired
	public GetCreatedMeetings(OperationHelper operationHelper, EnhancedItemsService itemsService) {
		this.operationHelper = operationHelper;
		this.itemsService = itemsService;
	}

	@Override
	@Transactional(readOnly = true)
	public Object invokeOperation(OperationBoundary operationBoundary) {
		int[] args = this.operationHelper.getPageAndSize(operationBoundary.getOperationAttributes());
		UserId userId = operationBoundary.getInvokedBy().getUserId();
		return this.itemsService.getAllByTypeAndCreatedBy(userId.toString(), ItemTypes.MEETING, args[1], args[0]);
	}
}
