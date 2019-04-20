package com.ioteg.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.OptionalFields;
import com.ioteg.users.User;

@Repository
public interface OptionalFieldsRepository extends CrudRepository<OptionalFields, Long>{
	@Query("SELECT o.owner FROM OptionalFields o WHERE o.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
}
