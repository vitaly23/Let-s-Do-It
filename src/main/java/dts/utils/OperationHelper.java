package dts.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import constants.Constants;
import constants.OperationAttributes;

import dts.data.ItemEntity;
import dts.data.OperationEntity;
import dts.data.UserEntity;
import dts.data.UserRole;

import exceptions.InvalidOperationException;

@Component
public class OperationHelper {

	private UserHelper userHelper;
	private ItemHelper itemHelper;

	@Autowired
	public OperationHelper(UserHelper userHelper, ItemHelper itemHelper) {
		this.userHelper = userHelper;
		this.itemHelper = itemHelper;
	}

	@Transactional(readOnly = true)
	public void ValidateOpertaionData(OperationEntity operationEntity) {
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

	public void ValidateOperationAttributes(Map<String, Object> operationAttributes, String[] attribute_names) {
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
		return new int[] { page, size };
	}

	public Object[] getNameAndLatAndLng(Map<String, Object> operationAttributes) {
		String itemName = (String) operationAttributes.remove(OperationAttributes.NAME);
		double lat = Double.parseDouble((String) operationAttributes.remove(OperationAttributes.LAT));
		double lng = Double.parseDouble((String) operationAttributes.remove(OperationAttributes.LNG));
		return new Object[] { itemName, lat, lng };
	}

}
