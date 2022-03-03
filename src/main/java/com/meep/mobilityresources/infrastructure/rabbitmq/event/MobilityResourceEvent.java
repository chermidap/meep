package com.meep.mobilityresources.infrastructure.rabbitmq.event;

import com.meep.mobilityresources.domain.entity.MobilityResourceTypeEnum;
import com.meep.mobilityresources.domain.entity.ResourceType;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class MobilityResourceEvent {
  private String id;

  private String name;

  private BigDecimal axisX;

  private BigDecimal axisY;

  private boolean realTimeData;

  private long companyZoneId;

  private MobilityResourceTypeEnum type;
}
