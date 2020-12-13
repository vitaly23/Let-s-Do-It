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

import boundaries.OperationBoundary;

@Service
public class OperationServiceImplementation implements OperationService {
	private String spaceName;
	private Map<Long,OperationEntity> operationStore;
	private OperationConverter operationEntityConverter;
	private AtomicLong nextId;

	
	@Autowired
	public void setEntityConverter(OperationConverter operationConverter,UsersService userService) {
		this.operationEntityConverter = operationConverter;
	}
	
	@Value("${spring.application.name:default}")
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	
	@PostConstruct
	public void init() {
		this.operationStore = Collections.synchronizedMap(new HashMap<>());
		this.nextId = new AtomicLong(0L);
	}

	@Override
	public Object invokeOpreation(OperationBoundary operation) {
		OperationEntity entity= new OperationEntity();
		Long newId = nextId.incrementAndGet();
		entity.getOperationId().setId(""+this.spaceName+newId);
		entity.setCreatedTimestamp(new Date());
		
		entity= this.operationEntityConverter.toEntity(operation);
		
		this.operationStore.put(newId, entity);

		return this.operationEntityConverter.FromEntity(entity);
	}

	@Override
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		return this.operationStore.values().
				stream().
				map(entity -> this.operationEntityConverter.FromEntity(entity)).
				collect(Collectors.toList());
	}

	@Override
	public void deleteAllActions(String adminSpace, String adminEmail) {
		this.operationStore.clear();
		System.err.println(operationStore);
	}
	
	public String getSpaceName() {
		return spaceName;
	}
	public OperationConverter getOperationEntityConverter() {
		return operationEntityConverter;
	}
	public void setOperationEntityConverter(OperationConverter operationEntityConverter) {
		this.operationEntityConverter = operationEntityConverter;
	}
	
	

}
