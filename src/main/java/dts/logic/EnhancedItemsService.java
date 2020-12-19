package dts.logic;

import java.util.List;

import boundaries.ItemBoundary;
import models.operations.ItemId;

public interface EnhancedItemsService extends ItemsService {

	public void bindChild(String managerSpace, String managerEmail, String itemSpace, String itemId, ItemId item);

	public List<ItemBoundary> getAllChildren(String userSpace, String userEmail, String itemSpace, String itemId);

	public List<ItemBoundary> getParents(String userSpace, String userEmail, String itemSpace, String itemId);

}
