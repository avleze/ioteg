package com.ioteg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioteg.model.ConfigurableEventType;

@Repository
public interface ConfigurableEventTypeRepository extends CrudRepository<ConfigurableEventType, Long>{

}
