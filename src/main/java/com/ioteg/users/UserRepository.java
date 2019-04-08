package com.ioteg.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long>{

	@Query("SELECT u FROM User u INNER JOIN u.channels c WHERE :id IN (SELECT e.id FROM c.configurableEventTypes e)")
	public Optional<User> findUserWithConfigurableEvent(@Param("id") UUID id);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.roles rol WHERE u.username = :username")
	public Optional<User> findByUsername(@Param("username") String username);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.roles LEFT JOIN FETCH u.channels rol WHERE u.username = :username")
	public Optional<User> findByUsernameWithChannels(@Param("username") String username);
}
