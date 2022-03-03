package com.meep.mobilityresources.infrastructure.rest;

import com.meep.mobilityresources.domain.entity.BikeStation;
import com.meep.mobilityresources.domain.entity.MobilityResource;
import com.meep.mobilityresources.domain.entity.MotoSharing;
import com.meep.mobilityresources.infrastructure.rest.dto.ResourceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MobilityResourceMapper {
  MotoSharing asMotoSharing(ResourceDTO resourceDTO);
  BikeStation asBikeStation(ResourceDTO resourceDTO);

}
