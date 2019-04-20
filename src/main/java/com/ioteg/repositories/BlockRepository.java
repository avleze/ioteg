package com.ioteg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioteg.model.Block;

@Repository
public interface BlockRepository extends CrudRepository<Block, Long>{

}
