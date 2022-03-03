package com.meep.mobilityresources.infrastructure.redis.mapper;

import com.meep.mobilityresources.domain.entity.MobilityResource;
import com.meep.mobilityresources.infrastructure.redis.model.MobilityResourceModel;
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
  MobilityResourceModel asMobilityResourceModel(MobilityResource mobilityResource);

  @Mapping(target = "mobilityResource", ignore = true)
  MobilityResource asMobilityResource(MobilityResourceModel vehicle);
}
