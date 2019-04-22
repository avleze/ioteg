package com.ioteg.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.ChannelType;
import com.ioteg.model.ConfigurableEventType;
import com.ioteg.model.User;

@Repository
public interface ChannelTypeRepository extends CrudRepository<ChannelType, Long>{
	@Query("SELECT u FROM User u WHERE :id IN u.channels")
	public Optional<User> findOwner(@Param("id") Long id);
	
	@Query("SELECT c.configurableEventTypes FROM ChannelType c WHERE c.id = :id")
	public List<ConfigurableEventType> findAllConfigurableEventsOf(@Param("id") Long id);
}
