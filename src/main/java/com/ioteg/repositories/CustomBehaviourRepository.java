package com.ioteg.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.CustomBehaviour;
import com.ioteg.model.RuleCustomBehaviour;
import com.ioteg.model.User;
import com.ioteg.model.VariableCustomBehaviour;

@Repository
public interface CustomBehaviourRepository extends CrudRepository<CustomBehaviour, Long>{
	@Query("SELECT c.owner FROM CustomBehaviour c WHERE c.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
	
	@Query("SELECT c.variables FROM CustomBehaviour c WHERE c.id = :id")
	public List<VariableCustomBehaviour> findAllVariablesOf(@Param("id") Long id);
	
	@Query("SELECT c.rules FROM CustomBehaviour c WHERE c.id = :id")
	public List<RuleCustomBehaviour> findAllRulesOf(@Param("id") Long id);
}
