package dts.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import constants.Constants;
import constants.MeetingAttributes;
import constants.OperationAttributes;
import dts.Application;
import dts.data.ItemEntity;
import dts.data.OperationEntity;
import dts.data.UserEntity;
import dts.data.UserRole;

import exceptions.InvalidOperationException;
import exceptions.TraineeNotFoundException;

@Component
public class OperationHelper {

	private Log log = LogFactory.getLog(Application.class);
	private UserHelper userHelper;
	private ItemHelper itemHelper;
	private Utils utils;

	@Autowired
	public OperationHelper(UserHelper userHelper, ItemHelper itemHelper, Utils utils) {
		this.userHelper = userHelper;
		this.itemHelper = itemHelper;
		this.utils = utils;
	}

	@Transactional(readOnly = true)
	public void validateOpertaionData(OperationEntity operationEntity) {
		if (operationEntity.getType() == null || operationEntity.getType().isEmpty()
				|| operationEntity.getInvokedBy() == null || operationEntity.getInvokedBy().isEmpty()
				|| operationEntity.getItem() == null || operationEntity.getItem().isEmpty()) {
			throw new InvalidOperationException("Operation type, invokedBy or Item can not be null or empty");
		}
		String[] userId = operationEntity.getInvokedBy().split(Constants.DELIMITER);
		UserEntity existingUser = this.userHelper.getSpecificUserWithRole(userId[0], userId[1], UserRole.PLAYER);
		String[] itemId = operationEntity.getItem().split(Constants.DELIMITER);
		ItemEntity existingItem = this.itemHelper.getActiveSpecificItem(itemId[0], itemId[1]);
	}

	public void validateOperationAttributes(Map<String, Object> operationAttributes, String[] attribute_names) {
		if (operationAttributes == null)
			throw new InvalidOperationException("Operation attributes is null");
		for (String attribute_name : attribute_names) {
			if (operationAttributes.get(attribute_name) == null)
				throw new InvalidOperationException("Operation attribute " + attribute_name + " is null");
			try {
				String attribute_value = (String) operationAttributes.get(attribute_name);
				if (attribute_value.isEmpty())
					throw new InvalidOperationException("Operation attribute " + attribute_name + " is empty");
			} catch (ClassCastException e) {
				throw new InvalidOperationException("Operation attribute " + attribute_name + " must be a string");
			}

		}
	}

	public int[] getPageAndSize(Map<String, Object> operationAttributes) {
		int page, size;
		if (operationAttributes == null) {
			page = 0;
			size = 10;
		} else {
			Object optionalPage = operationAttributes.get(Constants.PAGE);
			Object optionalSize = operationAttributes.get(Constants.SIZE);
			try {
				page = (optionalPage == null) ? 0 : (int) optionalPage;
				size = (optionalSize == null) ? 10 : (int) optionalSize;
			} catch (ClassCastException e) {
				throw new InvalidOperationException(Constants.PAGE + " and " + Constants.SIZE + " must be an int");
			}
		}
		this.log.debug("Setting Pagination parameters to: " + Constants.PAGE + "=" + page + " and " + Constants.SIZE
				+ "=" + size);
		return new int[] { page, size };
	}

	public Object[] getNameAndLatAndLng(Map<String, Object> operationAttributes) {
		String itemName = (String) operationAttributes.remove(OperationAttributes.NAME);
		double lat = Double.parseDouble((String) operationAttributes.remove(OperationAttributes.LAT));
		double lng = Double.parseDouble((String) operationAttributes.remove(OperationAttributes.LNG));
		return new Object[] { itemName, lat, lng };
	}

	public String increaseNumberOfParticipants(String itemAttributes) {
		return this.changeNumberOfParticipants(itemAttributes, 1);
	}

	public String decreaseNumberOfParticipants(String itemAttributes) {
		return this.changeNumberOfParticipants(itemAttributes, -1);
	}

	private String changeNumberOfParticipants(String itemAttributes, int amount) {
		try {
			Map<String, Object> meetingAttributes = this.utils.getJackson().readValue(itemAttributes, Map.class);
			String numberOfParticipants = (String) meetingAttributes.get(MeetingAttributes.NUMBER_OF_PARTICIPANTS);
			String newNumberOfParticipants = Integer.parseInt(numberOfParticipants) + amount + "";
			this.log.debug("The number of participants was changed from " + numberOfParticipants + " to "
					+ newNumberOfParticipants);
			meetingAttributes.put(MeetingAttributes.NUMBER_OF_PARTICIPANTS, newNumberOfParticipants);
			return this.utils.getJackson().writeValueAsString(meetingAttributes);
		} catch (JsonMappingException e) {
			throw new InvalidOperationException(e);
		} catch (JsonProcessingException e) {
			throw new InvalidOperationException(e);
		}
	}

	public void validateMeetingIsNotFull(String itemAttributes) {
		try {
			Map<String, Object> meetingAttributes = this.utils.getJackson().readValue(itemAttributes, Map.class);
			String maximumNumberOfParticipants = (String) meetingAttributes
					.get(MeetingAttributes.MAX_PARTICIPANTS_NUMBER);
			String numberOfParticipants = (String) meetingAttributes.get(MeetingAttributes.NUMBER_OF_PARTICIPANTS);
			if (Integer.parseInt(numberOfParticipants) >= Integer.parseInt(maximumNumberOfParticipants))
				throw new InvalidOperationException("The meeting maximum number of particpents was reached");
		} catch (JsonMappingException e) {
			throw new InvalidOperationException(e);
		} catch (JsonProcessingException e) {
			throw new InvalidOperationException(e);
		}
	}

	public Object getFoundTrainee(String userId, List<?> traineeList) {
		if (traineeList.isEmpty())
			throw new TraineeNotFoundException("Trainee that was created by '" + userId + "' does not exist");
		return traineeList.get(0);
	}

}
