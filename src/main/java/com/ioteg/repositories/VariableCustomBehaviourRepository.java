package com.ioteg.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.VariableCustomBehaviour;
import com.ioteg.users.User;

@Repository
public interface VariableCustomBehaviourRepository extends CrudRepository<VariableCustomBehaviour, Long>{
	@Query("SELECT v.owner FROM VariableCustomBehaviour v WHERE v.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
}
