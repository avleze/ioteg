package com.ioteg.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.ConfigurableEventType;
import com.ioteg.model.User;

@Repository
public interface ConfigurableEventTypeRepository extends CrudRepository<ConfigurableEventType, Long> {
	@Query("SELECT c.owner FROM ConfigurableEventType c WHERE c.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);

}
