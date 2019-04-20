package com.ioteg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioteg.model.Field;

@Repository
public interface FieldRepository extends CrudRepository<Field, Long>{

}
