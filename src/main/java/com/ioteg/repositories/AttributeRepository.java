package com.ioteg.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.Attribute;
import com.ioteg.users.User;

@Repository
public interface AttributeRepository extends CrudRepository<Attribute, Long>{
	
	@Query("SELECT a.owner FROM Attribute a WHERE a.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
}
