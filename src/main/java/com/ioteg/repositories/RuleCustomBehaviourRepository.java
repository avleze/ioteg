package com.ioteg.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.RuleCustomBehaviour;
import com.ioteg.model.User;

@Repository
public interface RuleCustomBehaviourRepository extends CrudRepository<RuleCustomBehaviour, Long>{
	@Query("SELECT r.owner FROM RuleCustomBehaviour r WHERE r.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
}
