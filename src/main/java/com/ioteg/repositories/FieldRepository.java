package com.ioteg.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.Attribute;
import com.ioteg.model.Field;
import com.ioteg.model.User;

@Repository
public interface FieldRepository extends CrudRepository<Field, Long>{
	@Query("SELECT f.owner FROM Field f WHERE f.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
	
	@Query("SELECT f.fields FROM Field f WHERE f.id = :id")
	public List<Field> findAllFieldsOf(@Param("id") Long id);
	
	@Query("SELECT f.attributes FROM Field f WHERE f.id = :id")
	public List<Attribute> findAllAttributesOf(@Param("id") Long id);
	
	@Query("SELECT f FROM Field f LEFT JOIN FETCH f.attributes WHERE f.id = :id")
	public Optional<Field> findByIdWithAttributes(@Param("id") Long id);
	
	@Query("SELECT f FROM Field f LEFT JOIN FETCH f.fields WHERE f.id = :id")
	public Optional<Field> findByIdWithFields(@Param("id") Long id);
}
