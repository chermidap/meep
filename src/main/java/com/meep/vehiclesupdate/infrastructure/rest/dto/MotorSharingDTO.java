package com.meep.vehiclesupdate.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MotorSharingDTO extends ResourceDTO {

  @JsonProperty("licencePlate")
  private String licencePlate;

  @JsonProperty("range")
  private long range;

  @JsonProperty("batteryLevel")
  private long batteryLevel;

  @JsonProperty("helmets")
  private long helmets;

  @JsonProperty("model")
  private String model;

  @JsonProperty("resourceImageId")
  private String resourceImageId;

  @JsonProperty("resourcesImagesUrls")
  private List<String> resourcesImagesUrls;
}
