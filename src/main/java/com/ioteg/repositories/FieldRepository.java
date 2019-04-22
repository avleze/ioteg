package com.ioteg.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.Field;
import com.ioteg.model.User;

@Repository
public interface FieldRepository extends CrudRepository<Field, Long>{
	@Query("SELECT f.owner FROM Field f WHERE f.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
}
