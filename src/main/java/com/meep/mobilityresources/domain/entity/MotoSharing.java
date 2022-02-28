package com.meep.mobilityresources.domain.entity;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class MotoSharing extends ResourceType {

  private String licencePlate;

  private long range;

  private long batteryLevel;

  private long helmets;

  private String model;

  private String resourceImageId;

  private List<String> resourcesImagesUrls;

  private String resourceType;
}
