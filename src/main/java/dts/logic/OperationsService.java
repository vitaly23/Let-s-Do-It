package dts.logic;

import java.util.List;

import boundaries.OperationBoundary;

public interface OperationsService {

	public Object invokeOpreation(OperationBoundary operation);

	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail);

	public void deleteAllActions(String adminSpace, String adminEmail);

}