package com.ioteg.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.model.User;

@Repository
public interface EventTypeRepository extends CrudRepository<EventType, Long>{
	@Query("SELECT e.owner FROM EventType e WHERE e.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
	
	@Query("SELECT e.blocks FROM EventType e WHERE e.id = :id")
	public List<Block> findAllBlocksOf(@Param("id") Long id);
	
	@Query("SELECT e FROM EventType e LEFT JOIN FETCH e.blocks WHERE e.id = :id")
	public Optional<EventType> findByIdWithBlocks(@Param("id") Long id);

}
