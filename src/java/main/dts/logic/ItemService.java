package main.dts.logic;


import java.util.List;


import main.boundaries.DigitalItemBoundary;



public interface ItemService { 

	

	public DigitalItemBoundary create(String managerSpace,String managerEmail,DigitalItemBoundary newItem);

	

	public DigitalItemBoundary update(String managerSpace,String managerEmail,String itemSpace,String itemId,DigitalItemBoundary update);

	

	public List<DigitalItemBoundary> getAll(String userSpace,String userEmail);

	

	public DigitalItemBoundary getSpecificItem(String userSpace,String userEmail,String itemSpace,String itemId);

	

	public void deleteAll(String adminSpace,String adminEmail);


}