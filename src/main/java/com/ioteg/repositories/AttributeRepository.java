package com.ioteg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioteg.model.Attribute;

@Repository
public interface AttributeRepository extends CrudRepository<Attribute, Long>{

}
