package com.ioteg.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ioteg.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

	@Query("SELECT u FROM User u LEFT JOIN FETCH u.channels channels WHERE u.id = :id")
	public Optional<User> findUserByIdWithChannels(@Param("id") Long id);
	
	@Query("SELECT u FROM User u INNER JOIN u.channels c WHERE :id IN (SELECT e.id FROM c.configurableEventTypes e)")
	public Optional<User> findUserWithConfigurableEvent(@Param("id") Long id);
	
	@Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :username")
	public Optional<User> findByUsername(@Param("username") String username);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.channels channels WHERE u.username = :username OR u.email = :username")
	public Optional<User> findByUsernameWithChannels(@Param("username") String username);
}
