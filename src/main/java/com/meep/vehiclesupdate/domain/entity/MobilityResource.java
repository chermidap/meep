package com.meep.vehiclesupdate.domain.entity;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class MobilityResource {

  private String id;

  private String name;

  private BigDecimal axisX;

  private BigDecimal axisY;

  private boolean realTimeData;

  private long companyZoneId;

  private ResourceType resourceType;
}
