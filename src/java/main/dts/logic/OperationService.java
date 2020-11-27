package main.dts.logic;


import java.util.List;


import main.boundaries.OperationBoundary;


public interface OperationService {

	

	public Object invokeOpreation(OperationBoundary operation);

	

	public List<OperationBoundary> getAllOperations(String adminSpace,String adminEmail);

	

	public void deleteAllActions(String adminSpace,String adminEmail);


}