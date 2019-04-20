package com.ioteg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioteg.model.EventType;

@Repository
public interface EventTypeRepository extends CrudRepository<EventType, Long>{

}
