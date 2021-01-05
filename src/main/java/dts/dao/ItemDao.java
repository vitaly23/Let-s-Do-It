package dts.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import dts.data.ItemEntity;

public interface ItemDao extends PagingAndSortingRepository<ItemEntity, String> {

	public List<ItemEntity> findAllByItemChildren_itemId(
			@Param("itemId") String itemId, 
			Pageable pageable);

	public List<ItemEntity> findAllByActiveTrueAndItemChildren_itemId(
			@Param("itemId") String itemId,
			Pageable pageable);

	public List<ItemEntity> findAllByItemParents_itemId(
			@Param("itemId") String itemId, 
			Pageable pageable);

	public List<ItemEntity> findAllByActiveTrueAndItemParents_itemId(
			@Param("itemId") String itemId, 
			Pageable pageable);

	public List<ItemEntity> findAllByNameLikeIgnoreCase(
			@Param("pattern") String pattern, 
			Pageable pageable);

	public List<ItemEntity> findAllByActiveTrueAndNameLikeIgnoreCase(
			@Param("pattern") String pattern,
			Pageable pageable);

	public List<ItemEntity> findAllByType(
			@Param("type") String type, 
			Pageable pageable);

	public List<ItemEntity> findAllByActiveTrueAndType(
			@Param("type") String type, 
			Pageable pageable);

	public List<ItemEntity> findAllByLatBetweenAndLngBetween(
			@Param("minLatDistance") double minLatDistance,
			@Param("maxLatDistance") double maxLatDistance, 
			@Param("minLngDistance") double minLngDistance,
			@Param("maxLngDistance") double maxLngDistance, 
			Pageable pageable);

	public List<ItemEntity> findAllByActiveTrueAndLatBetweenAndLngBetween(
			@Param("minLatDistance") double minLatDistance, 
			@Param("maxLatDistance") double maxLatDistance,
			@Param("minLngDistance") double minLngDistance, 
			@Param("maxLngDistance") double maxLngDistance,
			Pageable pageable);

	public Optional<ItemEntity> findByActiveTrueAndItemId(
			@Param("itemId") String itemId);

	public Optional<ItemEntity> findByActiveTrueAndTypeAndCreatedBy(
			@Param("type") String type,
			@Param("createdBy") String createdBy);

	public List<ItemEntity> findAllByActiveTrue(
			Pageable pageable);

}
