package dts.logic;

import java.util.List;

import boundaries.ItemBoundary;
import models.operations.ItemId;

public interface EnhancedItemsService extends ItemsService {

	public void bindChild(String managerSpace, String managerEmail, String itemSpace, String itemId, ItemId item);

	public List<ItemBoundary> getAllChildren(String userSpace, String userEmail, String itemSpace, String itemId, int size, int page);

	public List<ItemBoundary> getParents(String userSpace, String userEmail, String itemSpace, String itemId, int size, int page);

	public List<ItemBoundary> getAllByNamePattern(String userSpace, String userEmail, String namePattern, int size, int page);
	
	public List<ItemBoundary> getAllByType(String userSpace, String userEmail, String type, int size, int page);
	
	public List<ItemBoundary> getAllByLocation(String userSpace, String userEmail, double lat, double lng, double distance, int size, int page);
	
}
