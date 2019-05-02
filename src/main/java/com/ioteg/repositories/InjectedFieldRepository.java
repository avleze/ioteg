package com.ioteg.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.InjectedField;
import com.ioteg.model.User;

@Repository
public interface InjectedFieldRepository extends CrudRepository<InjectedField, Long>{
	
	@Query("SELECT i.owner FROM InjectedField i WHERE i.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
}
