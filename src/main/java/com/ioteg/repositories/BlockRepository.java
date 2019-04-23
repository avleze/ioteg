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
	
	@Query("SELECT b FROM Block b LEFT JOIN FETCH b.fields WHERE b.id = :id")
	public Optional<Block> findByIdWithFields(@Param("id") Long id);
	
	@Query("SELECT b FROM Block b LEFT JOIN FETCH b.optionalFields WHERE b.id = :id")
	public Optional<Block> findByIdWithOptionalFields(@Param("id") Long id);
	
	@Query("SELECT b FROM Block b LEFT JOIN FETCH b.injectedFields WHERE b.id = :id")
	public Optional<Block> findByIdWithInjectedFields(@Param("id") Long id);
}
