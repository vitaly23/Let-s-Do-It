package dts.logic;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dts.converter.OperationConverter;
import dts.data.OperationEntity;
import models.operations.OperationId;
import boundaries.OperationBoundary;

//@Service
public class OperationsServiceImplementation implements OperationsService {

	private String spaceName;
	private Map<String, OperationEntity> operationStore;
	private OperationConverter operationConverter;
	private AtomicLong idGenerator;

	@Autowired
	public void setEntityConverter(OperationConverter operationConverter) {
		this.operationConverter = operationConverter;
	}

	@Value("${spring.application.name:default_space_name}")
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	@PostConstruct
	public void init() {
		this.operationStore = Collections.synchronizedMap(new HashMap<>());
		this.idGenerator = new AtomicLong(0L);
	}

	@Override
	public Object invokeOpreation(OperationBoundary operation) {
		OperationEntity operationEntity = this.operationConverter.toEntity(operation);
		String id = "" + this.idGenerator.getAndIncrement();
		operationEntity.setOperationId(new OperationId(this.spaceName, id).toString());
		operationEntity.setCreatedTimestamp(new Date());
		this.operationStore.put(operationEntity.getOperationId().toString(), operationEntity);
		return this.operationConverter.toBoundary(operationEntity);
	}

	@Override
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail, int size, int page) {
		return this.operationStore.values()
				.stream()
				.map(entity -> this.operationConverter.toBoundary(entity))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteAllActions(String adminSpace, String adminEmail) {
		this.operationStore.clear();
		this.idGenerator = new AtomicLong(0L);
	}

}
