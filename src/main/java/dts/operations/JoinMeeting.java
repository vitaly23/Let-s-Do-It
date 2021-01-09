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

import constants.Constants;
import constants.ItemTypes;
import constants.OperationTypes;

import dts.Application;
import dts.dao.ItemDao;
import dts.data.ItemEntity;
import dts.data.UserEntity;
import dts.data.UserRole;
import dts.logic.EnhancedItemsService;
import dts.utils.ItemHelper;
import dts.utils.OperationHelper;
import dts.utils.UserHelper;

import exceptions.InvalidOperationException;

import models.operations.ItemId;
import models.users.UserId;

@Component(OperationTypes.JOIN_MEETING)
public class JoinMeeting implements Operations {

	private Log log = LogFactory.getLog(Application.class);
	private EnhancedItemsService itemsService;
	private ItemDao itemDao;
	private ItemHelper itemHelper;
	private OperationHelper operationHelper;
	private UserHelper userHelper;

	@Autowired
	public JoinMeeting(EnhancedItemsService itemsService, ItemDao itemDao, ItemHelper itemHelper,
			OperationHelper operationHelper, UserHelper userHelper) {
		this.itemsService = itemsService;
		this.itemDao = itemDao;
		this.itemHelper = itemHelper;
		this.operationHelper = operationHelper;
		this.userHelper = userHelper;
	}

	@Override
	@Transactional
	public Object invokeOperation(OperationBoundary operationBoundary) {
		UserId userId = operationBoundary.getInvokedBy().getUserId();
		ItemId meetingId = operationBoundary.getItem().getItemId();
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(userId.getSpace(), userId.getEmail(),
				UserRole.PLAYER);
		ItemEntity meetingEntity = this.itemHelper.getSpecificItem(meetingId.getSpace(), meetingId.getId());
		this.log.debug("Validating Meeting " + meetingEntity.getItemId() + " is not in full capacity");
		this.operationHelper.validateMeetingIsNotFull(meetingEntity.getItemAttributes());
		List<ItemEntity> traineeList = this.itemDao.findAllByActiveTrueAndTypeAndCreatedBy(ItemTypes.TRAINEE,
				userId.toString(), PageRequest.of(0, 1));
		ItemEntity traineeEntity = (ItemEntity) this.operationHelper.getFoundTrainee(userId.toString(), traineeList);
		if (meetingEntity.getItemChildren().contains(traineeEntity)) {
			throw new InvalidOperationException("Trainnee " + traineeEntity.getItemId()
					+ " is already joined to the Meeting " + meetingEntity.getItemId());
		}
		meetingEntity.setItemAttributes(
				this.operationHelper.increaseNumberOfParticipants(meetingEntity.getItemAttributes()));
		this.itemDao.save(meetingEntity);
		this.userHelper.changeUserRole(existingUser, UserRole.MANAGER);
		this.log.debug("Joining Trainee " + traineeEntity.getItemId() + " to Meeting " + meetingEntity.getItemId());
		String[] traineeId = traineeEntity.getItemId().split(Constants.DELIMITER);
		this.itemsService.bindChild(userId.getSpace(), userId.getEmail(), meetingId.getSpace(), meetingId.getId(),
				new ItemId(traineeId[0], traineeId[1]));
		this.userHelper.changeUserRole(existingUser, UserRole.PLAYER);
		return Optional.empty();
	}

}
