package com.ioteg.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.ChannelType;
import com.ioteg.users.User;

@Repository
public interface ChannelTypeRepository extends CrudRepository<ChannelType, Long>{
	@Query("SELECT u FROM User u WHERE :id IN u.channels")
	public Optional<User> findOwner(@Param("id") Long id);
}
