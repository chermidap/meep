package com.meep.mobilityresources.infrastructure.rabbitmq.event;

import com.meep.mobilityresources.domain.entity.MobilityResource;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class MobilityResourceUpdateEvent implements Serializable {

  private final String location;
  private final List<MobilityResourceEvent> vehiclesAdded;
  private final List<MobilityResourceEvent> vehiclesDeleted;

}
