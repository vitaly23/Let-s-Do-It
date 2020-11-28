package logic;

import java.util.Map;


import dts.data.ItemEntity;
import dts.data.OperationEntity;
import models.operations.Item;
import models.operations.ItemIdentifier;
import boundaries.DigitalItemBoundary;
import boundaries.OperationBoundary;

public class OperationConverter {

	//convert boundary to entity
	public OperationEntity toEntity(OperationBoundary operationBoundary) {
		OperationEntity entity= new OperationEntity();

		if(operationBoundary.getOperationId() != null) {
			entity.getOperationId().setId(""+Long.parseLong(operationBoundary.getOperationId().id));
		}
		if(operationBoundary.getItem() != null) {
			entity.setItem(new ItemEntity());
			entity.getItem().setItemId(operationBoundary.getItem().getItemId());
		}
		if(operationBoundary.getInvokedBy() != null) {
			entity.setInvokedBy(operationBoundary.getInvokedBy());
		}
		if(operationBoundary.getCreatedTimestamp()!=null) {
			entity.setCreatedTimestamp(operationBoundary.getCreatedTimestamp());
		}
		if(operationBoundary.getType()!= null) {
			entity.setType(operationBoundary.getType());
		}
		if(operationBoundary.getOperationAttributes() !=null) {
			entity.setOperationAttributes(operationBoundary.getOperationAttributes());
		}


		return entity;
	}
	//convert entity to boundary
	public OperationBoundary FromEntity(OperationEntity entity) {
		OperationBoundary boundary = new OperationBoundary();

		if(entity.getCreatedTimestamp()!=null) {
			boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		}

		if(entity.getItem() != null) {
			boundary.setItem(new Item());
			boundary.getItem().setItemId(entity.getItem().getItemId());
		}

		if(entity.getOperationId() != null) {
			boundary.setOperationId( new ItemIdentifier(entity.getOperationId().getSpace(), entity.getOperationId().getId()));
		}
		if(entity.getInvokedBy() != null) {
			boundary.setInvokedBy(entity.getInvokedBy());
		}

		if(entity.getType()!= null) {
			boundary.setType(entity.getType());
		}
		if(entity.getOperationAttributes() !=null) {
			boundary.setOperationAttributes(entity.getOperationAttributes());
		}

		return boundary;
	}
}
