package com.meep.mobilityresources.infrastructure.rabbitmq.mapper;

import com.meep.mobilityresources.domain.entity.MobilityResource;
import com.meep.mobilityresources.infrastructure.rabbitmq.event.MobilityResourceEvent;
import com.meep.mobilityresources.infrastructure.redis.model.MobilityResourceModel;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MobilityResourceEventMapper {

  @IterableMapping(qualifiedByName = "asMobilityResourceEvent")
  List<MobilityResourceEvent> asMobilityResourceEvents(List<MobilityResource> products);

  @Named("asMobilityResourceEvent")
  @Mapping(target = "type", source = "kindOfResource")
  MobilityResourceEvent asMobilityResourceEvent(MobilityResource mobilityResource);

}
