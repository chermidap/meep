package com.meep.mobilityresources.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceDTO {

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("x")
  private BigDecimal axisX;

  @JsonProperty("y")
  private BigDecimal axisY;

  @JsonProperty("realTimeData")
  private boolean realTimeData;

  @JsonProperty("companyZoneId")
  private long companyZoneId;

  @JsonProperty("resourceType")
  private String resourceType;

  @JsonProperty("station")
  private boolean station;

  @JsonProperty("availableResources")
  private long availableResources;

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

  @JsonProperty("spacesAvailable")
  private long spacesAvailable;

  @JsonProperty("allowDropoff")
  private boolean allowDropoff;

  @JsonProperty("bikesAvailable")
  private boolean bikesAvailable;

}
