package dts.operations;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boundaries.OperationBoundary;

import constants.ItemTypes;
import constants.OperationTypes;

import dts.Application;
import dts.dao.ItemDao;
import dts.data.ItemEntity;
import dts.utils.ItemHelper;
import dts.utils.OperationHelper;
import exceptions.InvalidOperationException;
import models.operations.ItemId;
import models.users.UserId;

@Component(OperationTypes.LEAVE_MEETING)
public class LeaveMeeting implements Operations {

	private Log log = LogFactory.getLog(Application.class);
	private ItemDao itemDao;
	private ItemHelper itemHelper;
	private OperationHelper operationHelper;

	@Autowired
	public LeaveMeeting(ItemDao itemDao, ItemHelper itemHelper, OperationHelper operationHelper) {
		this.itemDao = itemDao;
		this.itemHelper = itemHelper;
		this.operationHelper = operationHelper;
	}

	@Override
	@Transactional
	public Object invokeOperation(OperationBoundary operationBoundary) {
		UserId userId = operationBoundary.getInvokedBy().getUserId();
		ItemId meetingId = operationBoundary.getItem().getItemId();
		ItemEntity meetingEntity = this.itemHelper.getSpecificItem(meetingId.getSpace(), meetingId.getId());
		List<ItemEntity> traineeList = this.itemDao.findAllByActiveTrueAndTypeAndCreatedBy(ItemTypes.TRAINEE,
				userId.toString(), PageRequest.of(0, 1));
		ItemEntity traineeEntity = (ItemEntity) this.operationHelper.getFoundTrainee(userId.toString(), traineeList);
		if (!meetingEntity.getItemChildren().contains(traineeEntity)) {
			throw new InvalidOperationException("Trainnee " + traineeEntity.getItemId()
					+ " is not joined to the Meeting " + meetingEntity.getItemId());
		}
		if (meetingEntity.getCreatedBy().equals(traineeEntity.getCreatedBy())) {
			this.log.debug("Setting Meeting " + meetingEntity.getItemId() + " as inactive");
			meetingEntity.setActive(false);
			this.itemDao.save(meetingEntity);
		} else {
			this.log.debug(
					"Unbinding Trainee " + traineeEntity.getItemId() + " from Meeting " + meetingEntity.getItemId());
			meetingEntity.setItemAttributes(
					this.operationHelper.decreaseNumberOfParticipants(meetingEntity.getItemAttributes()));
			traineeEntity.removeParent(meetingEntity);
			this.itemDao.save(traineeEntity);
			meetingEntity.removeChild(traineeEntity);
			this.itemDao.save(meetingEntity);
		}
		return Optional.empty();
	}

}
