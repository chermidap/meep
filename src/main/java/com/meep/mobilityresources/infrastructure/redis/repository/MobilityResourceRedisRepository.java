package com.meep.mobilityresources.infrastructure.redis.repository;

import com.meep.mobilityresources.infrastructure.redis.model.MoibilityResourceModel;
import org.springframework.data.repository.CrudRepository;

public interface MobilityResourceRedisRepository extends CrudRepository<MoibilityResourceModel,String> { }
