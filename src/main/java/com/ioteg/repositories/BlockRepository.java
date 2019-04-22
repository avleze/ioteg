package com.ioteg.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.Block;
import com.ioteg.model.User;

@Repository
public interface BlockRepository extends CrudRepository<Block, Long>{
	@Query("SELECT b.owner FROM Block b WHERE b.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
}
