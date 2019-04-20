package com.ioteg.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.CustomBehaviour;
import com.ioteg.users.User;

@Repository
public interface CustomBehaviourRepository extends CrudRepository<CustomBehaviour, Long>{
	@Query("SELECT c.owner FROM CustomBehaviour c WHERE c.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
}
