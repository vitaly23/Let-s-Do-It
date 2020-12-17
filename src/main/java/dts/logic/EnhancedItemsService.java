package dts.logic;

import boundaries.ItemBoundary;

public interface EnhancedItemsService extends ItemsService {

	public void bindItemToChildItem(String managerSpace, String managerEmail, String itemSpace, String itemId);

	public ItemBoundary[] getAllItemChildren(String userSpace, String userEmail, String itemSpace, String itemId);

	public ItemBoundary[] getItemParents(String userSpace, String userEmail, String itemSpace, String itemId);

}
