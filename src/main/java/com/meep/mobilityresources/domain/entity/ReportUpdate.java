package com.meep.mobilityresources.domain.entity;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportUpdate {
  private List<MobilityResource> vehiclesAdded;
  private List<MobilityResource> vehiclesDeleted;
  private List<MobilityResource> currentMobilityResources;
}
