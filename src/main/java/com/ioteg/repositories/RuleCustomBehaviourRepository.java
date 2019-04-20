package com.ioteg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioteg.model.RuleCustomBehaviour;

@Repository
public interface RuleCustomBehaviourRepository extends CrudRepository<RuleCustomBehaviour, Long>{

}
