package dts.operations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boundaries.ItemBoundary;
import boundaries.OperationBoundary;

import constants.ItemTypes;
import constants.MeetingAttributes;
import constants.OperationAttributes;
import constants.OperationTypes;

import dts.logic.EnhancedItemsService;
import dts.utils.OperationHelper;

import exceptions.InvalidOperationException;

import models.operations.Location;
import models.users.UserId;

@Component(OperationTypes.FIND_MEETINGS)
public class FindMeetings implements Operations {

	private OperationHelper operationHelper;
	private EnhancedItemsService itemsService;

	@Autowired
	public FindMeetings(OperationHelper operationHelper, EnhancedItemsService itemsService) {
		this.operationHelper = operationHelper;
		this.itemsService = itemsService;
	}

	@Override
	@Transactional(readOnly = true)
	public Object invokeOperation(OperationBoundary operationBoundary) {
		this.operationHelper.ValidateOperationAttributes(operationBoundary.getOperationAttributes(),
				OperationAttributes.REQUIRED_FIND_MEETING_ATTRIBUTES);
		int[] args = this.operationHelper.getPageAndSize(operationBoundary.getOperationAttributes());
		UserId userId = operationBoundary.getInvokedBy().getUserId();
		ItemBoundary traineeBoundary = this.itemsService
				.getAllByTypeAndCreatedBy(userId.toString(), ItemTypes.TRAINEE, 1, 0).get(0);
		Location location = traineeBoundary.getLocation();
		Map<String, Object> operationAttributes = operationBoundary.getOperationAttributes();
		double distance = Double.parseDouble((String) operationAttributes.remove(OperationAttributes.DISTANCE));
		String typeOfSport = (String) operationAttributes.remove(OperationAttributes.TYPE_OF_SPORT);
		List<ItemBoundary> foundMeetings = this.itemsService.getAllMeetingsByLocationAndNotCreatedByAndTypeOfSport(
				userId.toString(), location.getLat(), location.getLng(), distance, typeOfSport, args[1], args[0]);
		Date currentDate = new Date();
		return foundMeetings.stream().filter(foundMeeting -> {
			String meetingEndDate = (String) foundMeeting.getItemAttributes().get(MeetingAttributes.MEETING_END_DATE);
			DateFormat df = new SimpleDateFormat(MeetingAttributes.MEETING_DATE_FORMAT);
			Date meetingDate;
			try {
				meetingDate = df.parse(meetingEndDate);
				if (currentDate.compareTo(meetingDate) > 0) {
					return false;
				}
				return true;
			} catch (ParseException e) {
				throw new InvalidOperationException("date fromat is invalid");
			}
		}).collect(Collectors.toList());
	}

}
