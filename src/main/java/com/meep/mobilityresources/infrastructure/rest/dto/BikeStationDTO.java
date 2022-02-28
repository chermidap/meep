package com.meep.mobilityresources.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BikeStationDTO extends ResourceDTO {

  @JsonProperty("station")
  private boolean station;

  @JsonProperty("availableResources")
  private long availableResources;

  @JsonProperty("spacesAvailable")
  private long spacesAvailable;

  @JsonProperty("allowDropoff")
  private boolean allowDropoff;

  @JsonProperty("bikesAvailable")
  private boolean bikesAvailable;
}
