package dts.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import constants.Constants;
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
}
