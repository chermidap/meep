package com.meep.mobilityresources.infrastructure.rabbitmq.event;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class MobilityResourceUpdateEvent implements Serializable {

  private final String location;
  private final List<MobilityResourceEvent> mobilityResourcesAdded;
  private final List<MobilityResourceEvent> mobilityResourcesDeleted;

}
