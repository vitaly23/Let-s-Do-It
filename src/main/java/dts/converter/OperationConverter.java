package dts.converter;

import dts.data.OperationEntity;
import dts.utils.Utils;
import models.operations.InvokedBy;
import models.operations.Item;
import models.operations.ItemId;
import models.operations.OperationId;
import models.users.UserId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import boundaries.OperationBoundary;
import constants.Constants;

@Component
public class OperationConverter {

	private Utils utils;

	@Autowired
	public OperationConverter(Utils utils) {
		this.utils = utils;
	}

	public OperationEntity toEntity(OperationBoundary operationBoundary) {
		OperationEntity entity = new OperationEntity();
		if (operationBoundary.getOperationId() != null) {
			entity.setOperationId(operationBoundary.getOperationId().toString());
		}
		if (operationBoundary.getItem() != null && operationBoundary.getItem().getItemId() != null) {
			entity.setItem(operationBoundary.getItem().toString());
		}
		if (operationBoundary.getType() != null) {
			entity.setType(operationBoundary.getType());
		}
		entity.setCreatedTimestamp(operationBoundary.getCreatedTimestamp());
		if (operationBoundary.getInvokedBy() != null && operationBoundary.getInvokedBy().getUserId() != null) {
			entity.setInvokedBy(operationBoundary.getInvokedBy().toString());
		}
		if (operationBoundary.getOperationAttributes() != null) {
			entity.setOperationAttributes(this.utils.toEntity(operationBoundary.getOperationAttributes()));
		}
		return entity;
	}

	public OperationBoundary toBoundary(OperationEntity operationEntity) {
		OperationBoundary boundary = new OperationBoundary();
		boundary.setCreatedTimestamp(operationEntity.getCreatedTimestamp());
		if (operationEntity.getItem() != null) {
			String[] args = operationEntity.getItem().split(Constants.DELIMITER);
			boundary.setItem(new Item(new ItemId(args[0], args[1])));
		}
		if (operationEntity.getOperationId() != null) {
			String[] args = operationEntity.getOperationId().split(Constants.DELIMITER);
			boundary.setOperationId(new OperationId(args[0], args[1]));
		}
		if (operationEntity.getInvokedBy() != null) {
			String[] args = operationEntity.getInvokedBy().split(Constants.DELIMITER);
			boundary.setInvokedBy(new InvokedBy(new UserId(args[0], args[1])));
		}
		if (operationEntity.getType() != null) {
			boundary.setType(operationEntity.getType());
		}
		if (operationEntity.getOperationAttributes() != null) {
			boundary.setOperationAttributes(this.utils.toBoundaryAsMap(operationEntity.getOperationAttributes()));
		}

		return boundary;
	}
}
