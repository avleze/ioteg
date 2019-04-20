package com.ioteg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioteg.model.OptionalFields;

@Repository
public interface OptionalFieldsRepository extends CrudRepository<OptionalFields, Long>{

}
