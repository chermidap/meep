package com.meep.mobilityresources.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class BikeStation extends ResourceType {

  private boolean station;

  private long availableResources;

  private long spacesAvailable;

  private boolean allowDropoff;

  private boolean bikesAvailable;

}
