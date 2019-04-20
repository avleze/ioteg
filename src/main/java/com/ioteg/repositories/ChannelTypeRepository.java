package com.ioteg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioteg.model.ChannelType;

@Repository
public interface ChannelTypeRepository extends CrudRepository<ChannelType, Long>{

}
