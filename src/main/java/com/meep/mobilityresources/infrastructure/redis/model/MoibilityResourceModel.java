package com.meep.mobilityresources.infrastructure.redis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("MobilityResource")
public class MoibilityResourceModel implements Serializable {

  private String id;

  private String name;

  private BigDecimal axisX;

  private BigDecimal axisY;

  private Boolean realTimeData;

  private Long companyZoneId;

  private String localization;

  private OffsetDateTime creationDate;
}
