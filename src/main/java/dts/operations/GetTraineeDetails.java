package dts.operations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boundaries.OperationBoundary;

import constants.ItemTypes;
import constants.OperationTypes;

import dts.converter.ItemConverter;
import dts.dao.ItemDao;
import dts.data.ItemEntity;

import exceptions.TraineeNotFoundException;

@Component(OperationTypes.GET_TRAINEE_DETAILS)
public class GetTraineeDetails implements Operations {

	private ItemDao itemDao;
	private ItemConverter itemConverter;

	@Autowired
	public GetTraineeDetails(ItemDao itemDao, ItemConverter itemConverter) {
		this.itemDao = itemDao;
		this.itemConverter = itemConverter;
	}

	@Override
	@Transactional(readOnly = true)
	public Object invokeOperation(OperationBoundary operationBoundary) {
		String userId = operationBoundary.getInvokedBy().getUserId().toString();
		Optional<ItemEntity> optionalTrainee = this.itemDao.findByActiveTrueAndTypeAndCreatedBy(ItemTypes.TRAINEE,
				userId);
		if (!optionalTrainee.isPresent()) {
			throw new TraineeNotFoundException("Trainee with userId: " + userId + " does not exist");
		}
		return this.itemConverter.toBoundary(optionalTrainee.get());
	}

}
