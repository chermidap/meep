package com.meep.mobilityresources.infrastructure.redis.repository;

import com.meep.mobilityresources.infrastructure.redis.model.MobilityResourceModel;
import org.springframework.data.repository.CrudRepository;

public interface MobilityResourceRedisRepository extends CrudRepository<MobilityResourceModel,String> { }
