package com.meep.mobilityresources.infrastructure.redis.mapper;

import com.meep.mobilityresources.domain.entity.MobilityResource;
import com.meep.mobilityresources.infrastructure.redis.model.MoibilityResourceModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MobilityResourceModelMapper {

  @Mapping(target = "localization", ignore = true)
  @Mapping(target = "creationDate", ignore = true)
  MoibilityResourceModel asVehicleModel(MobilityResource mobilityResource);

  @Mapping(target = "resourceType", ignore = true)
  MobilityResource asVehicle(MoibilityResourceModel vehicle);
}
