package dts.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boundaries.OperationBoundary;

import constants.OperationTypes;

import dts.logic.EnhancedItemsService;
import dts.utils.OperationHelper;

import models.operations.ItemId;
import models.users.UserId;

@Component(OperationTypes.GET_JOINED_MEETINGS)
public class GetJoinedMeetings implements Operations {

	private OperationHelper operationHelper;
	private EnhancedItemsService itemsService;

	@Autowired
	public GetJoinedMeetings(OperationHelper operationHelper, EnhancedItemsService itemsService) {
		this.operationHelper = operationHelper;
		this.itemsService = itemsService;
	}

	@Override
	@Transactional(readOnly = true)
	public Object invokeOperation(OperationBoundary operationBoundary) {
		int[] args = this.operationHelper.getPageAndSize(operationBoundary.getOperationAttributes());
		UserId userId = operationBoundary.getInvokedBy().getUserId();
		ItemId itemId = operationBoundary.getItem().getItemId();
		return this.itemsService.getAllJoinedMeetings(userId.toString(), itemId.toString(), args[1], args[0]);
	}

}
