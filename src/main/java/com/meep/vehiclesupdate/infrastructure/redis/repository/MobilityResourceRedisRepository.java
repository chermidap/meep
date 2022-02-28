package com.meep.vehiclesupdate.infrastructure.redis.repository;

import com.meep.vehiclesupdate.infrastructure.redis.model.MoibilityResourceModel;
import org.springframework.data.repository.CrudRepository;

public interface MobilityResourceRedisRepository extends CrudRepository<MoibilityResourceModel,String> { }
