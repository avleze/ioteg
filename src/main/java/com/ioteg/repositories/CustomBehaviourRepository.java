package com.ioteg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioteg.model.CustomBehaviour;

@Repository
public interface CustomBehaviourRepository extends CrudRepository<CustomBehaviour, Long>{

}
