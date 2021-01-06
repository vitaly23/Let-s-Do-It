package dts.operations;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boundaries.ItemBoundary;
import boundaries.OperationBoundary;

import constants.ItemTypes;
import constants.MeetingAttributes;
import constants.OperationTypes;

import dts.Application;
import dts.data.UserEntity;
import dts.data.UserRole;
import dts.logic.EnhancedItemsService;
import dts.utils.OperationHelper;
import dts.utils.UserHelper;

import models.users.UserId;

@Component(OperationTypes.CREATE_MEETING)
public class CreateMeeting implements Operations {

	private Log log = LogFactory.getLog(Application.class);
	private EnhancedItemsService itemsService;
	private OperationHelper operationHelper;
	private UserHelper userHelper;

	@Autowired
	public CreateMeeting(EnhancedItemsService itemsService, OperationHelper operationHelper, UserHelper userHelper) {
		this.itemsService = itemsService;
		this.operationHelper = operationHelper;
		this.userHelper = userHelper;
	}

	@Override
	@Transactional
	public Object invokeOperation(OperationBoundary operationBoundary) {
		this.operationHelper.ValidateOperationAttributes(operationBoundary.getOperationAttributes(),
				MeetingAttributes.ALL_MEETING_ATTRIBUTES);
		UserId userId = operationBoundary.getInvokedBy().getUserId();
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(userId.getSpace(), userId.getEmail(),
				UserRole.PLAYER);
		this.log.debug("Setting user with email '" + userId.getEmail() + "' to Manager role");
		this.userHelper.ChangeUserRole(existingUser, UserRole.MANAGER);
		Map<String, Object> operationAttributes = operationBoundary.getOperationAttributes();
		Object[] args = this.operationHelper.getNameAndLatAndLng(operationAttributes);
		ItemBoundary meetingBoundary = new ItemBoundary(ItemTypes.MEETING, (String) args[0], (double) args[1],
				(double) args[2], operationAttributes);
		this.log.debug("Creating new digital item of type " + ItemTypes.MEETING);
		ItemBoundary meeting = this.itemsService.create(userId.getSpace(), userId.getEmail(), meetingBoundary);
		ItemBoundary traineeBoundary = this.itemsService
				.getAllByTypeAndCreatedBy(userId.toString(), ItemTypes.TRAINEE, 1, 0).get(0);
		this.log.debug("Binding " + traineeBoundary.getName() + " to meeting " + meeting.getName());
		this.itemsService.bindChild(userId.getSpace(), userId.getEmail(), meeting.getItemId().getSpace(),
				meeting.getItemId().getId(), traineeBoundary.getItemId());
		this.log.debug("Setting user with email '" + userId.getEmail() + "' to Player role");
		this.userHelper.ChangeUserRole(existingUser, UserRole.PLAYER);
		return meeting;
	}

}
