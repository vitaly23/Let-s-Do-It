package dts.operations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import exceptions.InvalidOperationException;

import models.operations.ItemId;
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
		this.operationHelper.validateOperationAttributes(operationBoundary.getOperationAttributes(),
				MeetingAttributes.ALL_MEETING_ATTRIBUTES);
		Map<String, Object> operationAttributes = operationBoundary.getOperationAttributes();
		this.validateMaximumParticipantsNumber(
				(String) operationAttributes.get(MeetingAttributes.MAX_PARTICIPANTS_NUMBER));
		this.validateMeetingDateFormat((String) operationAttributes.get(MeetingAttributes.MEETING_START_DATE));
		this.validateMeetingDateFormat((String) operationAttributes.get(MeetingAttributes.MEETING_END_DATE));
		ItemId itemId = operationBoundary.getItem().getItemId();
		UserId userId = operationBoundary.getInvokedBy().getUserId();
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(userId.getSpace(), userId.getEmail(),
				UserRole.PLAYER);
		this.userHelper.changeUserRole(existingUser, UserRole.MANAGER);
		Object[] args = this.operationHelper.getNameAndLatAndLng(operationAttributes);
		operationAttributes.put(MeetingAttributes.NUMBER_OF_PARTICIPANTS, "1");
		ItemBoundary meetingBoundary = new ItemBoundary(ItemTypes.MEETING, (String) args[0], (double) args[1],
				(double) args[2], operationAttributes);
		this.log.debug("Creating new digital item of type " + ItemTypes.MEETING);
		ItemBoundary newMeeting = this.itemsService.create(userId.getSpace(), userId.getEmail(), meetingBoundary);
		ItemBoundary traineeBoundary = this.itemsService.getSpecificItem(userId.getSpace(), userId.getEmail(),
				itemId.getSpace(), itemId.getId());
		this.log.debug("Binding Trainee " + traineeBoundary.getItemId() + " to meeting " + newMeeting.getItemId());
		this.itemsService.bindChild(userId.getSpace(), userId.getEmail(), newMeeting.getItemId().getSpace(),
				newMeeting.getItemId().getId(), traineeBoundary.getItemId());
		this.userHelper.changeUserRole(existingUser, UserRole.PLAYER);
		return newMeeting;
	}

	public void validateMeetingDateFormat(String date) {
		try {
			new SimpleDateFormat(MeetingAttributes.MEETING_DATE_FORMAT).parse(date);
		} catch (ParseException e) {
			throw new InvalidOperationException("Date fromat is invalid");
		}
	}

	public void validateMaximumParticipantsNumber(String maximumNumberOfParticipants) {
		if (Integer.parseInt(maximumNumberOfParticipants) < 1)
			throw new InvalidOperationException("Maximum number of participants can not be less then 1");
	}

}
