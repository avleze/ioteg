package com.ioteg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioteg.model.VariableCustomBehaviour;

@Repository
public interface VariableCustomBehaviourRepository extends CrudRepository<VariableCustomBehaviour, Long>{

}
